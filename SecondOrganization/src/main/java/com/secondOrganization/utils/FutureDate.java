package com.secondOrganization.utils;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FutureDateValidatorImpl.class)
public @interface FutureDate {
    String message() default "Date must be in the future";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}

class FutureDateValidatorImpl implements ConstraintValidator<FutureDate, LocalDate> {
    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
        return date != null && date.isAfter(LocalDate.now());
    }
}