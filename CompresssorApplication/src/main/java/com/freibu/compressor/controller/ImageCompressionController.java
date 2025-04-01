package com.freibu.compressor.controller;

import com.freibu.compressor.service.ImageCompressionService;
import com.freibu.compressor.util.FileUtil;
import com.freibu.compressor.exception.FileCompressionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/compress")
public class ImageCompressionController {

    private static final Logger logger = LoggerFactory.getLogger(ImageCompressionController.class);

    @Autowired
    private ImageCompressionService imageCompressionService;

    @Autowired
    private FileUtil fileUtil;

    @PostMapping("/image")
    public String compressImage(@RequestParam("file") MultipartFile multipartFile) {
        logger.info("Received request to compress image: {}", multipartFile.getOriginalFilename());

        try {
            // Convert MultipartFile to File
            File file = fileUtil.convertMultipartFileToFile(multipartFile);

            // Validate file
            if (fileUtil.isValidImage(file)) {
                String outputFilePath = imageCompressionService.compressImage(file);
                logger.info("Image compression successful, file saved at: {}", outputFilePath);
                return "Image compressed successfully and saved to " + outputFilePath;
            } else {
                logger.error("Invalid image file: {}", file.getName());
                throw new FileCompressionException("Invalid image file.");
            }
        } catch (FileCompressionException | IOException e) {
            logger.error("Error during image compression: {}", e.getMessage(), e);
            return "Error: Image compression failed. Please try again.";
        }
    }
}
