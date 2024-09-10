package com.shop_Karol_Herzog_Banasik.product.services;

import com.shop_Karol_Herzog_Banasik.LocalDateTimeProvider;
import com.shop_Karol_Herzog_Banasik.exceptions.UnexpectedFileTypeException;
import com.shop_Karol_Herzog_Banasik.product.Image;
import com.shop_Karol_Herzog_Banasik.product.Product;
import com.shop_Karol_Herzog_Banasik.product.repositories.ImageRepository;
import com.shop_Karol_Herzog_Banasik.product.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
    private final String DIR_DEST = "D:\\images\\pdf\\test";


    /**
     * Allows saving .jpg .jpeg .png in file system and generating uniq name for all files and saving it into database
     *
     * @param files     files to save
     * @param productId product that corresponding with images
     * @throws IOException
     */
    @Transactional
    @Override
    public void addNewImageToFileSystem(MultipartFile[] files, Long productId) throws IOException {
        Product product = productRepository.findById(productId).orElseThrow();
        createDirectory(DIR_DEST);
        for (MultipartFile file : files) {
            Image imageEntity = new Image();
            String fileNameProvider = fileNameProvider(file);

            if (isValidFile(file)) {
                String filePath = DIR_DEST + "\\" + fileNameProvider;
                Path pathDir = Path.of(filePath);
                Files.write(pathDir, file.getBytes());
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
        return "size_%d_%s_%s".formatted(file.getSize(), formattedDateTime, file.getOriginalFilename());
    }

    private boolean isValidFile(MultipartFile file) {
        if (file.getOriginalFilename() != null) {
            return file.getOriginalFilename().toLowerCase().endsWith(".jpg") ||
                    file.getOriginalFilename().toLowerCase().endsWith(".jpeg") ||
                    file.getOriginalFilename().toLowerCase().endsWith(".png");
        }
        return false;
    }

    private void createDirectory(String direcotryDestination) throws IOException {
        File folder = new File(direcotryDestination);
        Path path = Paths.get(direcotryDestination);
        Files.createDirectories(path.getParent());
        if (!folder.exists()) {
            Files.createDirectories(path.getParent());
        }
    }
}
