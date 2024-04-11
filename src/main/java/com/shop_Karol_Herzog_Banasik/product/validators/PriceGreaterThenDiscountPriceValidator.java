package com.shop_Karol_Herzog_Banasik.product.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.math.BigDecimal;


public class PriceGreaterThenDiscountPriceValidator implements ConstraintValidator<PriceGreaterThenDiscountPrice, Object> {

    private String basePrice;
    private String discountPrice;


    @Override
    public void initialize(PriceGreaterThenDiscountPrice constraintAnnotation) {
        basePrice = constraintAnnotation.basePrice();
        discountPrice = constraintAnnotation.discountPrice();
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            BigDecimal baseValue = (BigDecimal) getFieldValue(value, basePrice);
            BigDecimal discountValue = (BigDecimal) getFieldValue(value, discountPrice);
            return baseValue.compareTo(discountValue) >= 0;
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return false;
        }
    }

    private Object getFieldValue(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }
}

