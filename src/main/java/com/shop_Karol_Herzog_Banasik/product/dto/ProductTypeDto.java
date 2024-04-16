package com.shop_Karol_Herzog_Banasik.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductTypeDto {

    private Long id;
    private String name;

    //that constructor is needed for correctly parsing by JACKSON
    // *DO NOT DELETE*
    public ProductTypeDto(String name) {
        this.name = name;
    }
}
