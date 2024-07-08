package com.fpoly.httc_sport.exception;

import com.fpoly.httc_sport.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	@ExceptionHandler(value = Exception.class)
	ResponseEntity<ApiResponse> exceptionHandling(Exception exception) {
		log.error("Exception: ", exception);
		ApiResponse apiResponse = new ApiResponse();
		
		apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
		apiResponse.setMessage(exception.getMessage());
		
		return ResponseEntity.badRequest().body(apiResponse);
	}
	
	@ExceptionHandler(value = AppException.class)
	ResponseEntity<ApiResponse> appExceptionHandling(AppException appException) {
		ErrorCode errorCode = appException.getErrorCode();
		ApiResponse ApiResponse = new ApiResponse();
		
		ApiResponse.setCode(errorCode.getCode());
		ApiResponse.setMessage(errorCode.getMessage());
		
		return ResponseEntity.status(errorCode.getStatusCode()).body(ApiResponse);
	}
	
	@ExceptionHandler(value = AccessDeniedException.class)
	ResponseEntity<ApiResponse> accessDeniedExceptionHandling(AccessDeniedException exception) {
		ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
		
		return ResponseEntity.status(errorCode.getStatusCode())
				.body(ApiResponse.builder()
						.code(errorCode.getCode())
						.message(errorCode.getMessage())
						.build());
	}
}
