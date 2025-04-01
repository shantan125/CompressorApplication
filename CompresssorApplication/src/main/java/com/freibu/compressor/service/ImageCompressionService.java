package com.freibu.compressor.service;

import com.freibu.compressor.exception.CompressionException;
import com.freibu.compressor.util.FileUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

@Service
public class ImageCompressionService {

    private static final Logger logger = LoggerFactory.getLogger(ImageCompressionService.class);

    public String compressImage(MultipartFile file) throws CompressionException {
        try {
            String originalFileName = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(originalFileName);

            // Validate the file type
            if (!"jpg".equalsIgnoreCase(extension) && !"jpeg".equalsIgnoreCase(extension) && !"png".equalsIgnoreCase(extension)) {
                throw new CompressionException("Unsupported file type: " + extension);
            }

            logger.info("Starting image compression for file: {}", originalFileName);

            // Save the original file to a temporary location
            File inputFile = FileUtil.saveTempFile(file);

            // Compress the image
            String compressedFilePath = FileUtil.getCompressedFilePath(inputFile.getName());
            Thumbnails.of(inputFile)
                    .size(800, 600) // Resize image to 800x600 (example)
                    .outputQuality(0.8) // Set the output quality (example)
                    .toFile(compressedFilePath);

            logger.info("Image compression successful for file: {}", originalFileName);
            return compressedFilePath;

        } catch (IOException e) {
            logger.error("Error while compressing the image", e);
            throw new CompressionException("Error during image compression: " + e.getMessage(), e);
        }
    }
}
