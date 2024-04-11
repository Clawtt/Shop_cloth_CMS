package com.example.shop_karolherzogbanasik.product.services;

import com.example.shop_karolherzogbanasik.exceptions.NoElementFoundException;
import com.example.shop_karolherzogbanasik.product.Product;
import com.example.shop_karolherzogbanasik.product.ProductRepository;
import com.example.shop_karolherzogbanasik.product.ProductType;
import com.example.shop_karolherzogbanasik.product.dto.ProductDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
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
        List<String> types = List.of("man", "adult", "summer");
        List<ProductType> productTypes = List.of(
                new ProductType(1L, types.get(0)),
                new ProductType(2L, types.get(1)),
                new ProductType(3L, types.get(2))
        );
        productDto = new ProductDto(
                null,
                "T-Shirt",
                BigDecimal.valueOf(100),
                types,
                BigDecimal.valueOf(90)
        );
        ArrayList<ProductType> typess = new ArrayList<>();
        productDto.getTypes()
                .stream()
                .map(type -> types)
                .collect(Collectors.toList());
        product = new Product(
                null,
                productDto.getName(),
                productDto.getPrice(),
                productDto.getDiscountPrice(),
                typess
        );
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
        ProductServiceImpl productService = new ProductServiceImpl(productRepository);
        when(productRepository.findAll(Mockito.any(Pageable.class))).thenReturn(products);

        //when
        List<ProductDto> allProducts = productService.getAllProducts(10);

        //then
        Assertions.assertThat(allProducts).isNotNull();
    }

    @Test
    @DisplayName("Should save new product in database")
    public void saveNewProduct() {
        //given
        when(productRepository.save(any(Product.class))).thenReturn(product);
        //when
        productService.saveNewProduct(productDto);
        //then
        Mockito.verify(productRepository).save(product);
    }

    @ParameterizedTest
    @DisplayName("Should get product DTO by id from database")
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
    @DisplayName("Should get product by id from database")
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    public void findProductById(Long id) {
        //give
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        //when
        Optional<Product> productById = productService.findProductById(id);
        //then
        Assertions.assertThat(productById).isPresent();
    }

    @ParameterizedTest
    @DisplayName("Should update product by id")
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    public void updateProduct(Long id) {
        //given
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);
        //when
        productService.updateProduct(productDto, id);
        //then
        Mockito.verify(productRepository).findById(id);
    }

    @Test
    @DisplayName("Should throw NoElementFoundException")
    public void shouldThrowNoElementFoundException() {
        //given
        long id = 0L;
        BDDMockito.given(productRepository.findById(id))
                .willThrow(new NoElementFoundException("product at %d id has no exist".formatted(id)));

        //when and then
        Assertions.assertThatThrownBy(() -> productService.updateProduct(productDto, id))
                .isInstanceOf(NoElementFoundException.class)
                .hasMessage("product at %d id has no exist".formatted(id));
    }

    @ParameterizedTest
    @DisplayName("Should delete product by id")
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    public void deleteProduct(Long id) {
        //given
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        //when
        productService.deleteProduct(id);
        //then
        Mockito.verify(productRepository).findById(id);
    }
}
