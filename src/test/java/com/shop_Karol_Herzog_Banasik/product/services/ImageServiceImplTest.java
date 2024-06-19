package com.shop_Karol_Herzog_Banasik.product.services;

import com.shop_Karol_Herzog_Banasik.product.Image;
import com.shop_Karol_Herzog_Banasik.product.Product;
import com.shop_Karol_Herzog_Banasik.product.ProductType;
import com.shop_Karol_Herzog_Banasik.product.repositories.ImageRepository;
import com.shop_Karol_Herzog_Banasik.product.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ImageServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageServiceImpl imageService;

    private Product product;


    @BeforeEach
    public void init() throws IOException {
        MockitoAnnotations.openMocks(this);
        List<ProductType> types = List.of(new ProductType(1L, "man"),
                new ProductType(2L, "summer"));
        product = Product.builder()
                .id(1L)
                .name("T-shirt")
                .price(BigDecimal.valueOf(100))
                .discountPrice(BigDecimal.valueOf(100))
                .types(types)
                .build();
    }


    @ParameterizedTest()
    @ValueSource(strings = {"image.jpg", "image.jpeg", "image.png", "image.pdf"})
    public void addNewImage_imagesAddSuccess_verifyCorrectSaveInDatabase(String files) throws IOException {
        //given
//        Path pathMock = mock(Path.class);
//        Path path = Paths.get("D:/Java Projects/Shop_KarolHerzogBanasik/images");
//        when(pathMock).thenReturn(Path.of("here/is/path/to/uploads/dir"));
        Files.createDirectories(Path.of("images/pdf/test"));

        product.setId(1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        MockMultipartFile mockMultipartFile = new MockMultipartFile(files,files.getBytes());
        MultipartFile[] images = {mockMultipartFile};
        //when
        imageService.addNewImage(images, product.getId());

        //then
        verify(imageRepository, times(1)).save(any(Image.class));

    }

}
