package com.example.shop_karolherzogbanasik.product.services;

import com.example.shop_karolherzogbanasik.product.Product;
import com.example.shop_karolherzogbanasik.product.dto.ProductDto;

import java.util.List;
import java.util.Optional;


public interface ProductService {
    List<ProductDto> getAllProducts();
    List<ProductDto> getAllProducts(int PageSize);

    void saveNewProduct(ProductDto productDto);

    Optional<ProductDto> getProductById(Long id);
    Optional<Product> findProductById(Long id);

    void updateProduct(ProductDto productDto, Long id);

    void deleteProduct(Long id);
}
