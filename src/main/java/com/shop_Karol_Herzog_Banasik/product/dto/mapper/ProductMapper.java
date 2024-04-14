package com.shop_Karol_Herzog_Banasik.product.dto.mapper;

import com.shop_Karol_Herzog_Banasik.product.Product;
import com.shop_Karol_Herzog_Banasik.product.dto.ProductDto;
import com.shop_Karol_Herzog_Banasik.product.dto.ProductTypeDto;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static ProductDto map(Product product) {

        List<ProductTypeDto> productTypeDtos = product.getTypes().stream()
                .map(productType -> new ProductTypeDto(
                        productType.getId(),
                        productType.getName()))
                .collect(Collectors.toList());


        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                productTypeDtos,
                product.getDiscountPrice());
    }
}
