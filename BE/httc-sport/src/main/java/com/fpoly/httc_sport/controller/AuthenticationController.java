package com.fpoly.httc_sport.controller;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import com.fpoly.httc_sport.dto.request.LoginRequest;
import com.fpoly.httc_sport.dto.request.RefreshRequest;
import com.fpoly.httc_sport.dto.request.RegisterRequest;
import com.fpoly.httc_sport.dto.response.ApiResponse;
import com.fpoly.httc_sport.dto.response.AuthenticationResponse;
import com.fpoly.httc_sport.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationController {
	AuthenticationService authenticationService;
	
	@PostMapping("sign-in")
	ApiResponse<AuthenticationResponse> signIn(@RequestBody LoginRequest request, HttpServletResponse response) throws NoSuchAlgorithmException {
		return ApiResponse.<AuthenticationResponse>builder()
				.result(authenticationService.authenticate(request, response))
				.build();
	}
	
	@PostMapping("outbound/google/authenticate")
	ApiResponse<AuthenticationResponse> googleOutboundAuthenticate(@RequestParam("code") String code, HttpServletResponse response) throws NoSuchAlgorithmException {
		return ApiResponse.<AuthenticationResponse>builder()
				.result(authenticationService.googleOutboundAuthenticate(code, response))
				.build();
	}
	
	@PostMapping("outbound/facebook/authenticate")
	ApiResponse<AuthenticationResponse> facebookOutboundAuthenticate(@RequestParam("code") String code, HttpServletResponse response) throws NoSuchAlgorithmException {
		return ApiResponse.<AuthenticationResponse>builder()
				.result(authenticationService.facebookOutboundAuthenticate(code, response))
				.build();
	}
	
	@PostMapping("sign-up")
	ApiResponse<String> signUp(@RequestBody RegisterRequest request, HttpServletRequest httpRequest) {
		String response = authenticationService.register(request, httpRequest);
		
		return ApiResponse.<String>builder()
				.message(response)
				.build();
	}
	
	@GetMapping("sign-up/resend-verification-token")
	ApiResponse<?> resendVerificationToken(@RequestParam("token") String oldToken, HttpServletRequest request) {
		String response = authenticationService.resendVerificationToken(oldToken, request);
		
		return ApiResponse.builder()
				.message(response)
				.build();
	}
	
	@GetMapping("sign-up/verify-email")
	ApiResponse<?> verifyEmail(@RequestParam("token") String token, HttpServletRequest request) {
		String result = authenticationService.validateEmailToken(token);
		
		if (result.contains("expired"))
			return ApiResponse.builder()
					.message("Link xác thực đã hết hạn")
					.result(token)
					.build();
		
		return ApiResponse.builder()
				.message("Account is verified now")
				.build();
	}
	
	@PutMapping("refresh-token")
	ApiResponse<AuthenticationResponse> refreshToken(HttpServletRequest request, HttpServletResponse response, @RequestBody RefreshRequest userId) throws ParseException, JOSEException, NoSuchAlgorithmException {
		return ApiResponse.<AuthenticationResponse>builder()
				.result(authenticationService.refreshToken(request, response, userId))
				.build();
	}
}
