package com.example.imageviewer.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final List<String> imagePaths = new ArrayList<>();
    private int currentIndex = 0;

    public ImageController() {
        String folderPath = "imageviewer\\src\\main\\resources\\images";
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            for (File file : folder.listFiles()) {
                if (file.isFile() && isImageFile(file.getName())) {
                    imagePaths.add(file.getAbsolutePath());
                }
            }
        }
    }

    private boolean isImageFile(String fileName) {
        String[] extensions = { "jpg", "jpeg", "png", "gif" };
        for (String ext : extensions) {
            if (fileName.toLowerCase().endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    @GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getNextImage() throws IOException {
        if (imagePaths.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        File imageFile = new File(imagePaths.get(currentIndex));
        currentIndex = (currentIndex + 1) % imagePaths.size(); // Loop back to the first image

        byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }
}
