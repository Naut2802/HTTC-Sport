package com.fpoly.httc_sport.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import com.fpoly.httc_sport.dto.request.LoginRequest;
import com.fpoly.httc_sport.dto.request.RefreshRequest;
import com.fpoly.httc_sport.dto.request.RegisterRequest;
import com.fpoly.httc_sport.dto.response.ApiResponse;
import com.fpoly.httc_sport.dto.response.AuthenticationResponse;
import com.fpoly.httc_sport.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Authentication Controller")
@Slf4j
public class AuthenticationController {
	AuthenticationService authenticationService;
	
	@NonFinal
	@Value("${spring.security.cors.cross.origin}")
	String CROSS_ORIGIN;
	
	@Operation(summary = "Sign-in", description = "API sign-in")
	@PostMapping("sign-in")
	ApiResponse<AuthenticationResponse> signIn(@Valid @RequestBody LoginRequest request, HttpServletResponse response) throws NoSuchAlgorithmException {
		log.info("[Authentication Controller - Sign in api] Starting sign in to system with username: {}", request.getUsername());
		var res = authenticationService.authenticate(request, response);
		log.info("[Authentication Controller - Sign in api] Signed");
		return ApiResponse.<AuthenticationResponse>builder()
				.result(res)
				.build();
	}
	
	@Operation(summary = "Sign-in with Google", description = "API sign-in with Google")
	@PostMapping("outbound/google/authenticate")
	ApiResponse<AuthenticationResponse> googleOutboundAuthenticate(@RequestParam("code") String code, HttpServletResponse response) throws NoSuchAlgorithmException {
		log.info("[Authentication Controller - Sign in with gmail api] Starting sign in to system with gmail");
		var res = authenticationService.googleOutboundAuthenticate(code, response);
		log.info("[Authentication Controller - Sign in with gmail api] Signed");
		return ApiResponse.<AuthenticationResponse>builder()
				.result(res)
				.build();
	}
	
	@Operation(summary = "Sign-up", description = "API sign-up new account")
	@PostMapping("sign-up")
	ApiResponse<String> signUp(@Valid @RequestBody RegisterRequest request, HttpServletRequest httpRequest) {
		log.info("[Authentication Controller - Sign up] Starting sign up to system");
		String response = authenticationService.register(request, httpRequest);
		log.info("[Authentication Controller - Sign up] Sign up successfully");
		return ApiResponse.<String>builder()
				.message(response)
				.build();
	}
	
	@Operation(summary = "Resend-verify-token", description = "API resend new verify token")
	@GetMapping("sign-up/resend-verification-token")
	ApiResponse<?> resendVerificationToken(@RequestParam("token") String oldToken, HttpServletRequest request) {
		String response = authenticationService.resendVerificationToken(oldToken, request);
		
		return ApiResponse.builder()
				.message(response)
				.build();
	}
	
	@Operation(summary = "Verify-email", description = "API verify email with token sent to email")
	@GetMapping("sign-up/verify-email")
	ApiResponse<?> verifyEmail(@RequestParam("token") String token, HttpServletResponse response) throws IOException {
		String result = authenticationService.validateEmailToken(token);
		
		if (result.contains("expired")) {
			log.error("[Authentication Controller - Verify Email] Verify link has expired");
			response.sendRedirect(CROSS_ORIGIN + "/auth-mail-error?token=" + token);
			return ApiResponse.builder()
					.message(result)
					.result(token)
					.build();
		}
		
		log.error("[Authentication Controller - Verify Email] Email verified");
		response.sendRedirect(CROSS_ORIGIN + "/auth-mail-success");
		return ApiResponse.builder()
				.message("Email đã được xác thực. Kích hoạt tài khoản thành công")
				.build();
	}
	
	@Operation(summary = "Refresh-jwt-token", description = "API refresh jwt token when access token expired")
	@PutMapping("refresh-token")
	ApiResponse<AuthenticationResponse> refreshToken(HttpServletRequest request, HttpServletResponse response, @Valid @RequestBody RefreshRequest userId) throws ParseException, JOSEException, NoSuchAlgorithmException {
		return ApiResponse.<AuthenticationResponse>builder()
				.result(authenticationService.refreshToken(request, response, userId))
				.build();
	}
}
