package com.example.shop_karolherzogbanasik.product.dto.mapper;

import com.example.shop_karolherzogbanasik.product.Product;
import com.example.shop_karolherzogbanasik.product.ProductType;
import com.example.shop_karolherzogbanasik.product.dto.ProductDto;

import java.util.stream.Collectors;

public class ProductMapper {

    public static ProductDto map(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getTypes()
                        .stream()
                        .map(ProductType::getName)
                        .collect(Collectors.toList()),
                product.getDiscountPrice());
    }
}
