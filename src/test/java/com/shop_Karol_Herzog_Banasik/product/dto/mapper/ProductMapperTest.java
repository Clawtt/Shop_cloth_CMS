package com.shop_Karol_Herzog_Banasik.product.dto.mapper;

import com.shop_Karol_Herzog_Banasik.product.Product;
import com.shop_Karol_Herzog_Banasik.product.ProductType;
import com.shop_Karol_Herzog_Banasik.product.dto.ProductDto;
import com.shop_Karol_Herzog_Banasik.product.dto.ProductTypeDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

public class ProductMapperTest {

    @Test
    public void shouldMap() {
        //given
        List<ProductType> types = List.of(
                new ProductType(1L, "man"),
                new ProductType(2L, "summer")
        );
        List<ProductTypeDto> typesDto = List.of(
                new ProductTypeDto(1L, "man"),
                new ProductTypeDto(2L, "summer")
        );
        Product product = Product.builder()
                .id(1L)
                .name("T-shirt")
                .price(BigDecimal.valueOf(200))
                .discountPrice(BigDecimal.valueOf(150))
                .types(types)
                .build();
        ProductDto productDto = ProductDto.builder()
                .id(1L)
                .name("T-shirt")
                .price(BigDecimal.valueOf(200))
                .discountPrice(BigDecimal.valueOf(150))
                .types(typesDto)
                .build();
        //when
        ProductDto mapper = ProductMapper.map(product);
        //then
        Assertions.assertThat(mapper).isEqualTo(productDto);
    }


}
