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
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Authentication Controller")
public class AuthenticationController {
	AuthenticationService authenticationService;
	
	@Operation(summary = "Sign-in", description = "API sign-in")
	@PostMapping("sign-in")
	ApiResponse<AuthenticationResponse> signIn(@Valid @RequestBody LoginRequest request, HttpServletResponse response) throws NoSuchAlgorithmException {
		return ApiResponse.<AuthenticationResponse>builder()
				.result(authenticationService.authenticate(request, response))
				.build();
	}
	
	@Operation(summary = "Sign-in with Google", description = "API sign-in with Google")
	@PostMapping("outbound/google/authenticate")
	ApiResponse<AuthenticationResponse> googleOutboundAuthenticate(@RequestParam("code") String code, HttpServletResponse response) throws NoSuchAlgorithmException {
		return ApiResponse.<AuthenticationResponse>builder()
				.result(authenticationService.googleOutboundAuthenticate(code, response))
				.build();
	}
	
	//Login with Facebook
//	@PostMapping("outbound/facebook/authenticate")
//	ApiResponse<AuthenticationResponse> facebookOutboundAuthenticate(@RequestParam("code") String code, HttpServletResponse response) throws NoSuchAlgorithmException {
//		return ApiResponse.<AuthenticationResponse>builder()
//				.result(authenticationService.facebookOutboundAuthenticate(code, response))
//				.build();
//	}
	
	@Operation(summary = "Sign-up", description = "API sign-up new account")
	@PostMapping("sign-up")
	ApiResponse<String> signUp(@Valid @RequestBody RegisterRequest request, HttpServletRequest httpRequest) {
		String response = authenticationService.register(request, httpRequest);
		
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
			response.sendRedirect("http://localhost:3000/auth-mail-error?token="+token);
			return ApiResponse.builder()
					.message(result)
					.result(token)
					.build();
		}
		
		response.sendRedirect("http://localhost:3000/auth-mail-success");
		return ApiResponse.builder()
				.message("Account is verified now")
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
