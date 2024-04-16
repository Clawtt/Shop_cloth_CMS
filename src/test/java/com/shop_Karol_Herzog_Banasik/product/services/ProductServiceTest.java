package com.shop_Karol_Herzog_Banasik.product.services;

import com.shop_Karol_Herzog_Banasik.exceptions.NoElementFoundException;
import com.shop_Karol_Herzog_Banasik.product.Product;
import com.shop_Karol_Herzog_Banasik.product.repositories.ProductRepository;
import com.shop_Karol_Herzog_Banasik.product.ProductType;
import com.shop_Karol_Herzog_Banasik.product.dto.ProductDto;
import com.shop_Karol_Herzog_Banasik.product.dto.ProductTypeDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    private ProductServiceImpl productService;

    private Product product;
    private ProductDto productDto;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductServiceImpl(productRepository);
        List<ProductTypeDto> typesDto = List.of(
                new ProductTypeDto(1L, "man"),
                new ProductTypeDto(2L, "summer"),
                new ProductTypeDto(3L, "woman")
        );
        productDto = new ProductDto(
                0L,
                "T-Shirt",
                BigDecimal.valueOf(100),
                typesDto,
                BigDecimal.valueOf(90)
        );
        List<ProductType> types = productDto.getTypes()
                .stream()
                .map(type -> new ProductType(type.getId(), type.getName()))
                .toList();

        product =  Product.builder()
                .id(null)
                .name(productDto.getName())
                .price(productDto.getPrice())
                .discountPrice(productDto.getDiscountPrice())
                .build();
    }


    @Test
    @DisplayName("Should get all products")
    public void getAllProducts() {
        //given
        when(productRepository.findAll()).thenReturn(List.of(product));
        //when
        List<ProductDto> allProducts = productService.getAllProducts();
        //then
        Assertions.assertThat(allProducts).isNotNull();
    }

    @Test
    @DisplayName("Should get all products with Pageable class")
    public void getAllProductsWithPage() {
        //given
        Page<Product> products = Mockito.mock(Page.class);
        when(productRepository.findAll(Mockito.any(Pageable.class))).thenReturn(products);

        //when
        List<ProductDto> allProducts = productService.getAllProducts(10);

        //then
        Assertions.assertThat(allProducts).isNotNull();
    }

    @Test
    @DisplayName("Should save new product")
    public void saveNewProduct() {
        //given
        when(productRepository.save(any(Product.class))).thenReturn(product);
        //when
        productService.saveNewProduct(productDto);
        //then
        Mockito.verify(productRepository).save(product);
    }

    @ParameterizedTest
    @DisplayName("Should get product by id")
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    public void getProductById(Long id) {
        //give
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        //when
        Optional<ProductDto> productById = productService.getProductById(id);
        //then
        Assertions.assertThat(productById).isPresent();
    }

    @ParameterizedTest
    @DisplayName("Should update product by id")
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    public void updateProduct(Long id) {
        //given
        long empty = 0L;
        when(productRepository.findById(empty)).thenReturn(Optional.empty());
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);
        //when
        productService.updateProduct(id, productDto);
        //then
        Mockito.verify(productRepository).findById(id);
        //when and then
        Assertions.assertThatThrownBy(() -> productService.updateProduct(empty, productDto))
                .isInstanceOf(NoElementFoundException.class)
                .hasMessage("product at %d id has no exist".formatted(empty));
    }

    @ParameterizedTest
    @DisplayName("Should delete product by id")
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    public void deleteProduct(Long id) {
        //given
        long empty = 0L;
        when(productRepository.findById(empty)).thenReturn(Optional.empty());
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        //when
        productService.deleteProduct(id);
        //then
        Mockito.verify(productRepository).findById(id);
        //when and then
        Assertions.assertThatThrownBy(() -> productService.deleteProduct(empty))
                .isInstanceOf(NoElementFoundException.class)
                .hasMessage("product at %d id has no exist".formatted(empty));
    }
}
