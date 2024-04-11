package com.example.shop_karolherzogbanasik.product.dto;

import com.example.shop_karolherzogbanasik.product.validators.PriceGreaterThenDiscountPrice;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Data
@PriceGreaterThenDiscountPrice(basePrice = "price", discountPrice = "discountPrice")
public class ProductDto {

    private Long id;

    @NotNull(message = "field name can not be null")
    private String name;

    @DecimalMin(value = "1")
    private BigDecimal price;


    private List<String> types;

    @DecimalMin(value = "1")
    private BigDecimal discountPrice;

}
