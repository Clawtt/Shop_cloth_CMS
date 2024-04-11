package com.example.shop_karolherzogbanasik.product.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PriceGreaterThenDiscountPriceValidator.class)
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PriceGreaterThenDiscountPrice {
    String message() default "Discount price can not be greater than base price";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String basePrice();

    String discountPrice();
}
