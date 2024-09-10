package com.shop_Karol_Herzog_Banasik.product.dto;

import com.shop_Karol_Herzog_Banasik.product.validators.ValidDiscountPrice;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
@ValidDiscountPrice(basePrice = "price", discountPrice = "discountPrice")
public class ProductDto {

    private Long id;

    @NotNull(message = "name can not be null")
    private String name;

    @DecimalMin(value = "1")
    private BigDecimal price;


    private List<ProductTypeDto> types;

    @DecimalMin(value = "1")
    private BigDecimal discountPrice;

}
