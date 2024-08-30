package com.shop_Karol_Herzog_Banasik.product.services;

import com.shop_Karol_Herzog_Banasik.LocalDateTimeProvider;
import com.shop_Karol_Herzog_Banasik.exceptions.UnexpectedFileTypeException;
import com.shop_Karol_Herzog_Banasik.product.Image;
import com.shop_Karol_Herzog_Banasik.product.Product;
import com.shop_Karol_Herzog_Banasik.product.ProductType;
import com.shop_Karol_Herzog_Banasik.product.repositories.ImageRepository;
import com.shop_Karol_Herzog_Banasik.product.repositories.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ImageServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ImageRepository imageRepository;
    @Mock
    private MultipartFile file;
    @Mock
    private LocalDateTimeProvider currentTime;
    @InjectMocks
    private ImageServiceImpl imageService;


    @BeforeEach
    public void init() throws IOException {
        MockitoAnnotations.openMocks(this);
    }


    @ParameterizedTest()
    @ValueSource(strings = {"image.jpg", "image.jpeg", "image.png"})
    public void addNewImageToFileSystem_ShouldSaveImage_WhenFileIsValid(String files) throws IOException {
        //given
        Product product = new Product();
        product.setId(1L);

        Image image = new Image();
        image.setProduct(product);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(file.getSize()).thenReturn(5555L);
        when(currentTime.currentTime()).thenReturn(LocalDateTime.now());
        when(file.getOriginalFilename()).thenReturn(files);
        when(file.getBytes()).thenReturn(new byte[]{1, 2, 3, 4, 5});

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        String formattedTime = formatter.format(currentTime.currentTime());;
        image.setPath("D:\\images\\pdf\\test\\" + "size_%d_%s_%s".formatted(
                file.getSize(), formattedTime, file.getOriginalFilename()));

        //when
        imageService.addNewImageToFileSystem(new MultipartFile[]{file}, product.getId());
        //then
        verify(imageRepository).save(image);
    }

    @ParameterizedTest()
    @ValueSource(strings = {"image.", "image.doc", "image.db", "image.dd"})
    public void addNewImageToFileSystem_ShouldThrownUnexpectedFileTypeException_WhenFileIsInvalid (String files) throws IOException {
        //given
        Product product = new Product();
        product.setId(1L);

        Image image = new Image();
        image.setProduct(product);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(file.getSize()).thenReturn(5555L);
        when(currentTime.currentTime()).thenReturn(LocalDateTime.now());
        when(file.getOriginalFilename()).thenReturn(files);
        when(file.getBytes()).thenReturn(new byte[]{1, 2, 3, 4, 5});

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        String formattedTime = formatter.format(currentTime.currentTime());;
        image.setPath("D:\\images\\pdf\\test\\" + "size_%d_%s_%s".formatted(
                file.getSize(), formattedTime, file.getOriginalFilename()));

        //when and then
        Assertions.assertThatThrownBy(
                        () -> imageService.addNewImageToFileSystem(
                                new MultipartFile[]{file},
                                product.getId()))
                .isInstanceOf(UnexpectedFileTypeException.class)
                .hasMessage(file.getOriginalFilename() + " is unexpected file type");

    }

}
