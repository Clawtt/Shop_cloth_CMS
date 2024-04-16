package com.shop_Karol_Herzog_Banasik.product.services;

import com.shop_Karol_Herzog_Banasik.LocalDateTimeProvider;
import com.shop_Karol_Herzog_Banasik.product.Image;
import com.shop_Karol_Herzog_Banasik.product.Product;
import com.shop_Karol_Herzog_Banasik.product.repositories.ImageRepository;
import com.shop_Karol_Herzog_Banasik.product.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    @Value("${image.upload.directory}")
    private String uploadDirectory;

    @Transactional
    @Override
    public void addNewImage(MultipartFile[] image, Long productId) throws IOException {
        Product product = productRepository.findById(productId).orElseThrow();
        for (MultipartFile f : image) {
            Image imageEntity = new Image();
            String fileName = fileNameProvider(f);
            byte[] imageBytes = f.getBytes();
            Path pathDir = Path.of(uploadDirectory, fileName);
            String filePath = Files.write(pathDir, imageBytes).toUri().getPath();
            imageEntity.setPath(filePath);
            imageEntity.setProduct(product);
            imageRepository.save(imageEntity);
        }

    }

    private String fileNameProvider(MultipartFile file) {
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.incrementAndGet();
        LocalDateTimeProvider currentTime = new LocalDateTimeProvider();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        String formattedDateTime = formatter.format(currentTime.currentTime());
        return "size_%d_%s_%s".formatted(file.getSize(), formattedDateTime, file.getOriginalFilename());
    }
}
