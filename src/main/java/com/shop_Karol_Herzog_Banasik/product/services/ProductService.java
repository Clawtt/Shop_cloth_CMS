package com.shop_Karol_Herzog_Banasik.product.services;

import com.shop_Karol_Herzog_Banasik.product.Product;
import com.shop_Karol_Herzog_Banasik.product.dto.ProductDto;

import java.util.List;
import java.util.Optional;


public interface ProductService {
    List<ProductDto> getAllProducts();
    List<ProductDto> getAllProducts(int PageSize);

    void saveNewProduct(ProductDto productDto);

    Optional<ProductDto> getProductById(Long id);
    Optional<Product> findProductById(Long id);

    void updateProductAndProductType(ProductDto productDto, Long id);

    void deleteProduct(Long id);
}
