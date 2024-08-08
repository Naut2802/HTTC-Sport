package com.fpoly.httc_sport.controller;

import java.io.IOException;
import java.util.List;

import com.fpoly.httc_sport.dto.request.*;
import com.fpoly.httc_sport.dto.response.ApiResponse;
import com.fpoly.httc_sport.dto.response.ChangePasswordResponse;
import com.fpoly.httc_sport.dto.response.UserResponse;
import com.fpoly.httc_sport.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "User Controller")
public class UserController {
	UserService userService;
	
	@Operation(summary = "Api change password")
	@PatchMapping("change-password/{userId}")
	ApiResponse<ChangePasswordResponse> changePassword(@PathVariable String userId, @Valid @RequestBody ChangePasswordRequest request) {
		ChangePasswordResponse response = userService.changePassword(userId, request);
		
		return ApiResponse.<ChangePasswordResponse>builder()
				.message(response.getMessage())
				.result(response)
				.build();
	}
	
	@GetMapping("my-info")
	ApiResponse<UserResponse> getMyInfo() {
		return ApiResponse.<UserResponse>builder()
				.result(userService.getMyInfo())
				.build();
	}
	
	@PatchMapping("{userId}")
	ApiResponse<UserResponse> updateProfileUser(@PathVariable String userId, @Valid @RequestBody UserUpdateProfileRequest request) {
		return ApiResponse.<UserResponse>builder()
				.result(userService.updateProfileUser(userId, request))
				.build();
	}
	
	@Operation(summary = "Api forgot password (step 1)", description = "Api use for send a verify token when email valid")
	@GetMapping("forgot-password")
	ApiResponse<?> checkEmail(@RequestParam("email") String email, HttpServletRequest request) {
		String response = userService.sendForgotPasswordEmail(email, request);
		
		return ApiResponse.builder()
				.message(response)
				.build();
	}
	
	@Operation(summary = "Api forgot password (step 2)", description = "Api use for verify token has sent to email from 'step 1'")
	@GetMapping("forgot-password/verify-token")
	ApiResponse<?> validateToken(@RequestParam("token") String token, HttpServletResponse response) throws IOException {
		String result = userService.validateForgotPasswordToken(token);
		
		if (result.contains("expired")) {
			response.sendRedirect("http://localhost:3000/forgot-password-verify-error");
			return ApiResponse.builder()
					.message(result)
					.build();
		}
		
		response.sendRedirect("http://localhost:3000/forgot-password-verify-success?token="+token);
		return ApiResponse.builder()
				.message(result)
				.result(token)
				.build();
	}
	
	@Operation(summary = "Api forgot password (step 3)", description = "Api use for reset password with verify token from 'step 2'")
	@PostMapping("forgot-password/reset-password")
	ApiResponse<ChangePasswordResponse> resetPassword(@RequestParam("token") String token, @Valid @RequestBody ResetPasswordRequest request) {
		ChangePasswordResponse response = userService.resetPassword(token, request);
		
		return ApiResponse.<ChangePasswordResponse>builder()
				.message(response.getMessage())
				.result(response)
				.build();
	}
	
	@Operation(summary = "Api get a user by user-id", description = "Admin use this api")
	@GetMapping("{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<UserResponse> getUser(@PathVariable String userId) {
		return ApiResponse.<UserResponse>builder()
				.result(userService.getUser(userId))
				.build();
	}
	
	@Operation(summary = "Api update a user by user-id", description = "Admin use this api")
	@PutMapping("{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<UserResponse> updateUser(@PathVariable String userId, @Valid @RequestBody UserUpdateRequest request) {
		return ApiResponse.<UserResponse>builder()
				.result(userService.updateUser(userId, request))
				.build();
	}
	
	@Operation(summary = "Api authority a user by user-id", description = "Admin use this api")
	@PutMapping("authorize/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<UserResponse> authorizeUser(@PathVariable String userId, @Valid @RequestBody AuthorizeUserRequest request) {
		return ApiResponse.<UserResponse>builder()
				.result(userService.authorizeUser(userId, request))
				.build();
	}
	
	@Operation(summary = "Api deactivate a user by user-id", description = "Admin use this api")
	@DeleteMapping("{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<String> deleteUser(@PathVariable String userId) {
		userService.deleteUser(userId);
		return ApiResponse.<String>builder()
				.result("User has been deleted")
				.build();
	}
	
	@Operation(summary = "Api get users", description = "Admin use this api")
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<List<UserResponse>> getUsers(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		return ApiResponse.<List<UserResponse>>builder()
				.result(userService.getUsers(page, size))
				.build();
	}
}
