package com.freibu.compressor.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

@Component
public class FileUtil {

    private static final String IMAGE_OUTPUT_DIR = "C:/Freibu-Image-Compressor-Image"; // Update if needed

    public File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        // Convert multipart file to a temporary file
        File file = File.createTempFile("upload-", multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
        return file;
    }

    public boolean isValidImage(File file) {
        // Validate image types based on file extensions
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png");
    }

    /**
     * Creates output directory if it does not exist.
     *
     * @return The created or existing directory.
     */
    public File createOutputDir(String imageOutputDir) {
        File directory = new File(IMAGE_OUTPUT_DIR);
        if (!directory.exists()) {
            boolean created = directory.mkdirs(); // Ensures all missing directories are created
            if (created) {
                System.out.println("Output directory created: " + IMAGE_OUTPUT_DIR);
            } else {
                System.err.println("Failed to create output directory: " + IMAGE_OUTPUT_DIR);
            }
        }
        return directory;
    }
}
