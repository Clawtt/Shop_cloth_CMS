package com.shop_Karol_Herzog_Banasik.product.dto.mapper;

import com.shop_Karol_Herzog_Banasik.product.Product;
import com.shop_Karol_Herzog_Banasik.product.ProductType;
import com.shop_Karol_Herzog_Banasik.product.dto.ProductDto;

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
