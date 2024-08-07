package com.fpoly.httc_sport.exception.annotations;

import com.fpoly.httc_sport.exception.validator.NotBlankAndSizeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NotBlankAndSizeValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlankAndSizeAndPattern {
	String message() default "INVALID";
	String regexp() default ".*";
	int min() default 0;
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
