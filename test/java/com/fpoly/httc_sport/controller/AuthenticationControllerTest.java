package com.fpoly.httc_sport.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpoly.httc_sport.dto.request.LoginRequest;
import com.fpoly.httc_sport.dto.request.RefreshRequest;
import com.fpoly.httc_sport.dto.request.RegisterRequest;
import com.fpoly.httc_sport.dto.response.ApiResponse;
import com.fpoly.httc_sport.dto.response.AuthenticationResponse;
import com.fpoly.httc_sport.dto.response.RoleResponse;
import com.fpoly.httc_sport.service.AuthenticationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class AuthenticationControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AuthenticationService authService;
	
	private LoginRequest loginRequest;
	private RegisterRequest regisRequest;
	private AuthenticationResponse authResponse;
	
	@BeforeEach
	void initData() {
		//String[] roles = {roleResponse.getRoleName(), roleResponse.getDescription()};
		//System.out.println(roles);
		loginRequest = LoginRequest.builder()
				.username("chaunt")
				.password("123456")
				.build();
		
		regisRequest = RegisterRequest.builder()
				.username("user")
				.password("123456")
				.email("chauthanhnguyen838@gmail.com")
				.build();
		
		authResponse = AuthenticationResponse.builder()
				.userId("5b3c42ad-80b6-4071-9d13-37aafab00714")
				.accessToken("eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJIVFRDX1Nwb3J0Iiwic3ViIjoiNWIzYzQyYWQtODBiNi00MDcxLTlkMTMtMzdhYWZhYjAwNzE0IiwiZXhwIjoxNzIxMjc2MTk4LCJpYXQiOjE3MjEyNzUyOTgsInNjb3BlIjoiUk9MRV9VU0VSIn0.F1SJIYRTOqKvSBLLwmkXD-579tDbk0IQMlXxkJhkfrRwhBt57rz7st02W25f72mvO1uDfv5zXI2RRFLWiQUAGM3b1--Vt0w02Fz0GyqKjdreNwbIumf5fmLaTvB8ZMh_oHUIQnyY6HQPtvYKaKPivI87sJcEYQVvQvIM9o2thJv01WpnCL_nYFPjUbo8_3mFiQHNWmj1cX32XhUiyTkCqMsXJQU1ZJBKK-3271yc0Qfe6ZbeAG04IxQ-8gUyG-Y8l8znjPhGV8lxwACAxIhQ2-ePD0oork-l7z7h7kTTkH62t_r8Y3g7av9UgXzCbX9RQkxr36HupmWTZRgU7OFH0Q")
				.authenticated(true)
				.build();
	}
	
	@Test
	// Kiểm tra đăng nhập thành công
	void testSignIn_Success() throws Exception{
		// GIVEN - Đầu vào
		ObjectMapper objectMapper = new ObjectMapper();
		//HttpServletResponse httpResponse = mock(HttpServletResponse.class);
		String content = objectMapper.writeValueAsString(loginRequest);
		Mockito.when(authService.authenticate(loginRequest, mock(HttpServletResponse.class))).thenReturn(authResponse);
		// WHEN,THEN - Khi test api
		mockMvc.perform(MockMvcRequestBuilders
				.post("/api/auth/sign-in")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(content))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("code")
						.value(1000)
		);
		// THEN - Khi request xảy ra thì sẽ nhận được gì
	}
	
	@Test
	// Kiểm tra xác minh tài khoản gg thành công
	@WithMockUser(username="chaunt", roles= {"USER"})
	void testGoogleOutboundAuthenticate() throws Exception {
		String codeEmail = "codeEmail";
		when(authService.googleOutboundAuthenticate(ArgumentMatchers.anyString(), ArgumentMatchers.any(HttpServletResponse.class)))
		.thenReturn(authResponse);
		mockMvc.perform(post("/api/auth/outbound/google/authenticate")
				.param("code", codeEmail)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("code").value(1000)
				);
		verify(authService, times(1)).googleOutboundAuthenticate(ArgumentMatchers.anyString(), ArgumentMatchers.any(HttpServletResponse.class));
	}
	
	@Test
	// Kiểm tra đăng kí tài khoản thành công
	void testSignUp_Success() throws Exception{
		// GIVEN
		String message = "Đăng ký tài khoản thành công, vui lòng xác thực tài khoản thông qua email đã đăng ký";
		ObjectMapper objectMapper = new ObjectMapper();
		String content = objectMapper.writeValueAsString(regisRequest);
		Mockito.when(authService.register(regisRequest, mock(HttpServletRequest.class))).thenReturn(message);
		// WHEN
		mockMvc.perform(MockMvcRequestBuilders
				.post("/api/auth/sign-up")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(content))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("code")
						.value(1000)
		);
	}
	
	@Test
	public void testResendVerificationToken() throws Exception {
		String oldToken = "old-token";
		String serviceResponse = "Token Resent";

		when(authService.resendVerificationToken(anyString(), any(HttpServletRequest.class)))
				.thenReturn(serviceResponse);

		mockMvc.perform(get("/api/auth/sign-up/resend-verification-token")
				.param("token", oldToken)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value(serviceResponse));

		verify(authService, times(1)).resendVerificationToken(anyString(), any(HttpServletRequest.class));
	}
	
//	@Test
//	public void testVerifyEmail() throws Exception {
//		String token = "tokenTest";
//
//		when(authService.validateEmailToken(ArgumentMatchers.anyString()));
//
//		mockMvc.perform(get("/api/auth/sign-up/verify-email")
//				.param("token", token)
//				.contentType(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(status().isOk()
//				);
//
//		verify(authService, times(1)).validateEmailToken(ArgumentMatchers.anyString());
//	}

	@Test
	void testRefreshToken() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		// Mock dữ liệu trả về từ service
		AuthenticationResponse mockResponse = AuthenticationResponse.builder().userId("5b3c42ad-80b6-4071-9d13-37aafab00714").accessToken("accessToken")
				.authenticated(true).build();

		when(authService.refreshToken(any(), any(), any(RefreshRequest.class))).thenReturn(mockResponse);

		// Tạo request payload
		RefreshRequest refreshRequest = RefreshRequest.builder().userId("5b3c42ad-80b6-4071-9d13-37aafab00714").build();

		// Thực hiện cuộc gọi API và kiểm tra kết quả
		mockMvc.perform(put("/api/auth/refresh-token").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(refreshRequest)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.result.userId").value(mockResponse.getUserId()))
				.andExpect(jsonPath("$.result.accessToken").value(mockResponse.getAccessToken()))
				.andExpect(jsonPath("$.result.authenticated").value(mockResponse.isAuthenticated()));
	}
	
	@Test
	void testUnit() {
		log.info("Hello Test");
	}
}
