package com.freibu.compressor.util;

import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static File saveTempFile(MultipartFile file) throws IOException {
        String uploadDir = "C:/Freibu-Image-Compressor-Image/";
        File tempFile = new File(uploadDir + file.getOriginalFilename());
        logger.info("Saving file to: {}", tempFile.getAbsolutePath());
        file.transferTo(tempFile);
        return tempFile;
    }

    public static String getCompressedFilePath(String originalFileName) {
        String uploadDir = "C:/Freibu-Image-Compressor-Image/";
        String compressedFileName = "compressed-" + originalFileName;
        String compressedFilePath = uploadDir + compressedFileName;
        logger.info("Compressed file will be saved to: {}", compressedFilePath);
        return compressedFilePath;
    }
}
