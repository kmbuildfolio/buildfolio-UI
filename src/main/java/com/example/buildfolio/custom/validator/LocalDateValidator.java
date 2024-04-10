package com.example.buildfolio.custom.validator;

import com.example.buildfolio.custom.annotation.ValidLocalDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class LocalDateValidator implements ConstraintValidator<ValidLocalDate, LocalDate> {
    @Override
    public void initialize(ValidLocalDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return value != null && !value.isAfter(LocalDate.now());
    }
}
