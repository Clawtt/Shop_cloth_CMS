package com.shop_Karol_Herzog_Banasik.product.validators;

import jakarta.validation.ConstraintValidatorContext;
import lombok.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PriceGreaterThenDiscountPriceValidatorTest {


    @Test
    @DisplayName("Should return true if base price is equal or greater than discount price")
    public void isValidWithCorrectValues() {
        //given
        PriceGreaterThenDiscountPrice priceValidatorMock = mock(PriceGreaterThenDiscountPrice.class);
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        when(priceValidatorMock.basePrice()).thenReturn("basePrice");
        when(priceValidatorMock.discountPrice()).thenReturn("discountPrice");

        PriceGreaterThenDiscountPriceValidator validator = new PriceGreaterThenDiscountPriceValidator();
        validator.initialize(priceValidatorMock);

        Product product = new Product(BigDecimal.valueOf(100), BigDecimal.valueOf(60));
        //when
        boolean valid = validator.isValid(product, context);
        //then
        Assertions.assertThat(valid).isTrue();
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    private static class Product {
        private BigDecimal basePrice;
        private BigDecimal discountPrice;
    }
}
