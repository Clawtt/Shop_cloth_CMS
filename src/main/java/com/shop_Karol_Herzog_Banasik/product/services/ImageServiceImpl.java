package com.shop_Karol_Herzog_Banasik.product.services;

import com.shop_Karol_Herzog_Banasik.LocalDateTimeProvider;
import com.shop_Karol_Herzog_Banasik.exceptions.UnexpectedFileTypeException;
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
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    @Value("${image.upload.directory}")
    private String uploadDirectory;
//    private String DEFAULT_UPLOAD_DIRECTORY = "images";


    /**
     * Allows saving .jpg .jpeg .png .pdf in hardware and generating uniq name for all files and saving them into database
     * Method create default directory in work directory named images. You can customize path of directory using
     * application.properties: image.upload.directory=custom/your/directory.
     *
     * @param image     images to save
     * @param productId product that corresponding with images
     * @throws IOException
     */

    @Transactional
    @Override
    public void addNewImage(MultipartFile[] image, Long productId) throws IOException {
        Product product = productRepository.findById(productId).orElseThrow();
        createDirectory(uploadDirectory);
        for (MultipartFile file : image) {
            Image imageEntity = new Image();
            String fileNameProvider = fileNameProvider(file);
            byte[] imageBytes = file.getBytes();

            if (isValidFile(file)) {
                Path pathDir = Path.of(uploadDirectory, fileNameProvider);
                String filePath = Files.write(pathDir, imageBytes).toUri().getPath();
                imageEntity.setPath(filePath);
                imageEntity.setProduct(product);
                imageRepository.save(imageEntity);
            } else {
                throw new UnexpectedFileTypeException(file.getOriginalFilename() + " is unexpected file type");
            }
        }
    }
    private String fileNameProvider(MultipartFile file) {
        LocalDateTimeProvider currentTime = new LocalDateTimeProvider();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        String formattedDateTime = formatter.format(currentTime.currentTime());
        return "  size_%d_%s_%s".formatted(file.getSize(), formattedDateTime, file.getOriginalFilename());
    }

    private boolean isValidFile(MultipartFile file) {
        if (file.getOriginalFilename() != null) {
            return file.getOriginalFilename().toLowerCase().endsWith(".jpg") ||
                    file.getOriginalFilename().toLowerCase().endsWith(".jpeg") ||
                    file.getOriginalFilename().toLowerCase().endsWith(".png") ||
                    file.getOriginalFilename().toLowerCase().endsWith(".pdf");
        }
        return true;
    }

    private void createDirectory(String directory) throws IOException {
        Path pathDirectory = Paths.get(directory);
        if (!Files.exists(pathDirectory)) {
            Files.createDirectories(pathDirectory);
        } else {
            throw new RuntimeException();
        }
    }
}
