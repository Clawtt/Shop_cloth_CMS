package com.shop_Karol_Herzog_Banasik.product.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PriceGreaterThenDiscountPriceValidator.class)
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PriceGreaterThenDiscountPrice {
    String message() default "${validatedValue} discountPrice can't be greater than field price";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String basePrice();

    String discountPrice();
}
