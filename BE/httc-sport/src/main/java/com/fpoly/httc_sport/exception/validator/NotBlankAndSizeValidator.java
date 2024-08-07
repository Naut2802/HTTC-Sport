package com.fpoly.httc_sport.exception.validator;

import com.fpoly.httc_sport.exception.annotations.NotBlankAndSizeAndPattern;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class NotBlankAndSizeValidator implements ConstraintValidator<NotBlankAndSizeAndPattern, String> {
	private int min;
	private String message;
	private String regexp;
	
	@Override
	public void initialize(NotBlankAndSizeAndPattern constraintAnnotation) {
		this.min = constraintAnnotation.min();
		this.message = constraintAnnotation.message();
		this.regexp = constraintAnnotation.regexp();
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtils.isBlank(value)) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message + "_NULL").addConstraintViolation();
			return false;
		}
		if (value.length() < min) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message + "_INVALID_1").addConstraintViolation();
			return false;
		}
		if (!value.matches(regexp)) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message + "_INVALID_2").addConstraintViolation();
			return false;
		}
		return true;
	}
}
