package com.fpoly.httc_sport.exception;

import com.fpoly.httc_sport.dto.response.ApiResponse;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	private static final String MIN_ATTRIBUTE = "min";
	private static final String MAX_ATTRIBUTE = "max";
	
	@ExceptionHandler(value = Exception.class)
	ResponseEntity<ApiResponse<?>> exceptionHandling(Exception exception) {
		log.error("Exception: ", exception);
		
		return ResponseEntity.badRequest().body(ApiResponse.builder()
				.code(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode())
				.message(exception.getMessage())
				.build());
	}
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	ResponseEntity<ApiResponse<?>> handlingValidation(MethodArgumentNotValidException exception) {
		String key = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();
		ErrorCode errorCode = ErrorCode.INVALID_KEY;
		Map attributes = null;
		
		try {
			errorCode = ErrorCode.valueOf(key);
			var constraintViolation = exception.getBindingResult()
					.getAllErrors().getFirst().unwrap(ConstraintViolation.class);
			
			attributes = constraintViolation.getConstraintDescriptor().getAttributes();
			log.error(attributes.toString());
		} catch (IllegalArgumentException e) {
			log.error(e.toString());
		}

		return ResponseEntity.badRequest().body(ApiResponse.builder()
				.code(errorCode.getCode())
				.message(Objects.nonNull(attributes) ?
						mapAttribute(errorCode.getMessage(), attributes) : errorCode.getMessage())
				.build());
	}
	
	@ExceptionHandler(value = AppException.class)
	ResponseEntity<ApiResponse<?>> appExceptionHandling(AppException appException) {
		ErrorCode errorCode = appException.getErrorCode();
		
		return ResponseEntity.status(errorCode.getStatusCode()).body(ApiResponse.builder()
				.code(errorCode.getCode())
				.message(errorCode.getMessage())
				.build());
	}
	
	@ExceptionHandler(value = AccessDeniedException.class)
	ResponseEntity<ApiResponse<?>> accessDeniedExceptionHandling(AccessDeniedException exception) {
		ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
		
		return ResponseEntity.status(errorCode.getStatusCode())
				.body(ApiResponse.builder()
						.code(errorCode.getCode())
						.message(errorCode.getMessage())
						.build());
	}
	
	private String mapAttribute(String message, Map<String, Object> attributes) {
		String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));
		
		return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
	}
}
