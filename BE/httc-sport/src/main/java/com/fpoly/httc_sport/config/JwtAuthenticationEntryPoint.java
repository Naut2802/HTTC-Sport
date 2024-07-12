package com.fpoly.httc_sport.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpoly.httc_sport.dto.response.ApiResponse;
import com.fpoly.httc_sport.exception.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;
		
		if (authException.getCause() instanceof JwtException && authException.getCause().getMessage().equals("Token expired")) {
			errorCode = ErrorCode.TOKEN_EXPIRED;
			log.info(authException.getCause().getMessage());
		}
		
		response.setStatus(errorCode.getStatusCode().value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
		ApiResponse<?> responseApi = ApiResponse.builder()
				.code(errorCode.getCode())
				.message(errorCode.getMessage())
				.build();
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		response.getWriter().write(objectMapper.writeValueAsString(responseApi));
		response.flushBuffer();
	}
}
