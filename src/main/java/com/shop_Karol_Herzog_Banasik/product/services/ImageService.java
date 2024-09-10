package com.shop_Karol_Herzog_Banasik.product.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    void addNewImageToFileSystem(MultipartFile[] image, Long productId) throws IOException;

}
