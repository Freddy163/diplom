package com.example.fishop.controller;
import org.springframework.core.io.Resource;
import com.example.fishop.model.Image;
import com.example.fishop.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private ImageRepository imageRepository;

    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        // Используем Spring Resource вместо jakarta.annotation.Resource
        Resource file = new ClassPathResource("static/images/" + filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg") // или "image/png"
                .body(file);
    }
}
