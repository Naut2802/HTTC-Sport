package com.fpoly.httc_sport.controller;

import java.io.IOException;
import java.util.List;

import com.fpoly.httc_sport.dto.request.*;
import com.fpoly.httc_sport.dto.response.ApiResponse;
import com.fpoly.httc_sport.dto.response.ChangePasswordResponse;
import com.fpoly.httc_sport.dto.response.UserResponse;
import com.fpoly.httc_sport.entity.ChatMessage;
import com.fpoly.httc_sport.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "User Controller")
@Slf4j
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
	
	@GetMapping("chat-room/{roomId}")
	ApiResponse<List<ChatMessage>> getChatMessages(@PathVariable int roomId) {
		return ApiResponse.<List<ChatMessage>>builder()
				.result(userService.getChatMessagesByRoom(roomId))
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
		log.info("[User Controller - Reset password api] Password has been reset");
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
		log.info("[User Controller - Admin update user api] Admin updating a user with user-id: {}", userId);
		var response = userService.updateUser(userId, request);
		log.info("[User Controller - Admin update user api] User updated");
		return ApiResponse.<UserResponse>builder()
				.result(response)
				.build();
	}
	
	@Operation(summary = "Api authority a user by user-id", description = "Admin use this api")
	@PutMapping("authorize/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<UserResponse> authorizeUser(@PathVariable String userId, @Valid @RequestBody AuthorizeUserRequest request) {
		log.info("[User Controller - Admin authorize user api] Authorizing a user with user-id: {}", userId);
		var response = userService.authorizeUser(userId, request);
		log.info("[User Controller - Admin authorize user api] User authorized");
		return ApiResponse.<UserResponse>builder()
				.result(response)
				.build();
	}
	
	@Operation(summary = "Api deactivate a user by user-id", description = "Admin use this api")
	@DeleteMapping("{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<String> deleteUser(@PathVariable String userId) {
		log.info("[User Controller - Admin deactivate user api] Deactivate a user with user-id: {}", userId);
		var response = userService.deleteUser(userId);
		log.info("[User Controller - Admin deactivate user api] {}", response);
		return ApiResponse.<String>builder()
				.message(response)
				.build();
	}
	
	@Operation(summary = "Api active a user by user-id", description = "Admin use this api")
	@PatchMapping("/active/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<String> activeUser(@PathVariable String userId) {
		log.info("[User Controller - Admin active user api] Deactivate a user with user-id: {}", userId);
		var response = userService.activeUser(userId);
		log.info("[User Controller - Admin active user api] {}", response);
		return ApiResponse.<String>builder()
				.message(response)
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
