package com.shop_Karol_Herzog_Banasik.product.controllers;

import com.shop_Karol_Herzog_Banasik.product.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;
    @PostMapping(value = "/product/{id}/add/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImage(@RequestParam(name = "files") MultipartFile[] file,
                                              @PathVariable Long id) throws IOException {

        imageService.addNewImageToFileSystem(file, id);
        return ResponseEntity.ok().build();
    }

}
