package com.freibu.compressor.controller;

import com.freibu.compressor.service.ImageCompressionService;
import com.freibu.compressor.exception.CompressionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/compress")
public class ImageCompressionController {

    private static final Logger logger = LoggerFactory.getLogger(ImageCompressionController.class);

    @Autowired
    private ImageCompressionService imageCompressionService;

    @PostMapping("/image")
    public ResponseEntity<String> compressImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            logger.warn("Received empty file upload request");
            return ResponseEntity.badRequest().body("No file uploaded");
        }

        logger.info("Received image upload request for file: {}", file.getOriginalFilename());

        try {
            // Additional file validation can be done here if necessary
            String compressedFilePath = imageCompressionService.compressImage(file);
            logger.info("Image compression successful, file saved at: {}", compressedFilePath);
            return ResponseEntity.ok("Image compressed successfully. File saved at: " + compressedFilePath);
        } catch (CompressionException e) {
            logger.error("Image compression failed for file: {}", file.getOriginalFilename(), e);
            return ResponseEntity.status(500).body("Image compression failed: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error occurred during image compression for file: {}", file.getOriginalFilename(), e);
            return ResponseEntity.status(500).body("Unexpected error occurred: " + e.getMessage());
        }
    }
}
