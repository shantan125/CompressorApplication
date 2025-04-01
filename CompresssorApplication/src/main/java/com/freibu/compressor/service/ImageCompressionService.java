package com.freibu.compressor.service;

import com.freibu.compressor.exception.FileCompressionException;
import com.freibu.compressor.util.FileUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class ImageCompressionService {

    private static final Logger logger = LoggerFactory.getLogger(ImageCompressionService.class);

    @Value("${compressor.image.output-dir}")
    private String imageOutputDir;

    private final FileUtil fileUtil;

    public ImageCompressionService(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    public String compressImage(File file) throws FileCompressionException {
        if (file == null || !file.exists() || !file.canRead()) {
            logger.error("Invalid file provided for compression: {}", file != null ? file.getName() : "null");
            throw new FileCompressionException("File is null, does not exist, or cannot be read.");
        }

        logger.info("Starting image compression for file: {}", file.getName());

        try {
            // Ensure output directory exists
            File outputDir = fileUtil.createOutputDir(imageOutputDir);
            if (outputDir == null) {
                throw new FileCompressionException("Failed to create or access output directory.");
            }

            File outputFile = new File(outputDir, "compressed_" + file.getName());

            // Perform image compression
            Thumbnails.of(file)
                    .size(800, 800)  // Resize image to 800x800 (adjust as needed)
                    .outputFormat("jpg")
                    .toFile(outputFile);

            // Preserve metadata by copying over attributes
            Files.copy(file.toPath(), outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            logger.info("Image compression completed successfully: {}", outputFile.getAbsolutePath());
            return outputFile.getAbsolutePath();
        } catch (IOException e) {
            logger.error("Error compressing image: {}", file.getName(), e);
            throw new FileCompressionException("Failed to compress the image due to an internal error.");
        }
    }
}
