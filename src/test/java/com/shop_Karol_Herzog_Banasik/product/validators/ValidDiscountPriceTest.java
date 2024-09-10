package com.shop_Karol_Herzog_Banasik.product.validators;

import jakarta.validation.ConstraintValidatorContext;
import lombok.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ValidDiscountPriceTest {


    @Test
    public void checkDiscountPrice_ShouldReturnTrue_WhenPriceIsGreaterDiscountPrice() {
        //given
        final String BASE_PRICE_FIELD_NAME = "basePrice";
        final String DISCOUNT_PRICE_FIELD_NAME = "basePrice";
        final BigDecimal BASE_PRICE = BigDecimal.valueOf(100);
        final BigDecimal DISCOUNT_PRICE = BigDecimal.valueOf(100);

        ValidDiscountPrice priceValidatorMock = mock(ValidDiscountPrice.class);
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        when(priceValidatorMock.basePrice()).thenReturn(BASE_PRICE_FIELD_NAME);
        when(priceValidatorMock.discountPrice()).thenReturn(DISCOUNT_PRICE_FIELD_NAME);

        ValidDiscountPriceImpl validator = new ValidDiscountPriceImpl();
        validator.initialize(priceValidatorMock);

        Product product = new Product(BASE_PRICE, DISCOUNT_PRICE);
        //when
        boolean isValid = validator.isValid(product, context);
        //then
        Assertions.assertThat(isValid).isTrue();
    }
    @Test
    public void checkDiscountPrice_ShouldReturnTrue_WhenPriceIsEqualDiscountPrice() {
        //given
        final String BASE_PRICE_FIELD_NAME = "basePrice";
        final String DISCOUNT_PRICE_FIELD_NAME = "discountPrice";
        final BigDecimal BASE_PRICE = BigDecimal.valueOf(100);
        final BigDecimal DISCOUNT_PRICE = BigDecimal.valueOf(100);

        ValidDiscountPrice priceValidatorMock = mock(ValidDiscountPrice.class);
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        when(priceValidatorMock.basePrice()).thenReturn(BASE_PRICE_FIELD_NAME);
        when(priceValidatorMock.discountPrice()).thenReturn(DISCOUNT_PRICE_FIELD_NAME);

        ValidDiscountPriceImpl validator = new ValidDiscountPriceImpl();
        validator.initialize(priceValidatorMock);

        Product product = new Product(BASE_PRICE, DISCOUNT_PRICE);
        //when
        boolean isValid = validator.isValid(product, context);
        //then
        Assertions.assertThat(isValid).isTrue();
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    private static class Product {
        private BigDecimal basePrice;
        private BigDecimal discountPrice;
    }
}
