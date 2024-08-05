package com.fpoly.httc_sport.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpoly.httc_sport.dto.request.*;
import com.fpoly.httc_sport.dto.response.*;
import com.fpoly.httc_sport.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;
	
    // Request
	private ChangePasswordRequest changePassRequest;
	private UserUpdateProfileRequest userUpdateProfileRequest;
	private UserUpdateRequest userUpdateRequest;
	private AuthorizeUserRequest authRequest;
	private ResetPasswordRequest resetPasswordRequest;
	// Response
	private ChangePasswordResponse changePasswordResponse;
	private UserResponse userResponse;
	private List<UserResponse> listUsers;
	
	@BeforeEach
	void initData() {
		changePassRequest = ChangePasswordRequest.builder()
				.currentPassword("654321")
				.newPassword("123456")
				.confirmationPassword("123456")
				.build();
		
		changePasswordResponse = ChangePasswordResponse.builder()
				.username("chaunt")
				.message("Thay đổi mật khẩu thành công")
				.isChanged(true)
				.build();
		
		userUpdateProfileRequest = UserUpdateProfileRequest.builder()
				.firstName("Chau")
				.lastName("Nguyen")
				.phoneNumber("0909090909")
				.build();
		
		userUpdateRequest = UserUpdateRequest.builder()
				.email("chauthanhnguyen838@gmail.com")
				.firstName("Chau")
				.lastName("Nguyen")
				.phoneNumber("0916921559")
				.build();

		resetPasswordRequest = ResetPasswordRequest.builder()
				.newPassword("123456")
				.confirmationPassword("123456")
				.build();
	}
	
//	@Test
//	@WithMockUser(username = "chaunt", roles = {"USER"})
//	void testChangePassword() throws Exception{
//		// GIVE
//		ObjectMapper objectMapper = new ObjectMapper();
//		String content = objectMapper.writeValueAsString(changePassRequest);
//		// WHEN
//		Mockito.when(userService.changePassword(ArgumentMatchers.anyString(), ArgumentMatchers.any(ChangePasswordRequest.class)))
//			.thenReturn(changePasswordResponse);
//		// THEN
//		mockMvc.perform(MockMvcRequestBuilders
//				.patch("/api/v1/user/change-password/{userId}", "662154df-ebe9-4732-900e-953bdbc22203")
//				.contentType(MediaType.APPLICATION_JSON_VALUE)
//				.content(content))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("code")
//						.value(1000)				
//		);
//	}
	
//	@Test
//	@WithMockUser(username = "chaunt", roles = {"USER"})
//	void testGetMyInfo() throws Exception{
//		// GIVEN
//		
//		// WHEN
//		Mockito.when(userService.getMyInfo()).thenReturn(userResponse);
//		// THEN
//		mockMvc.perform(MockMvcRequestBuilders
//				.get("/api/v1/user/my-info")
//				.contentType(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("code")
//						.value(1000)
//			);
//	}
		
//	@Test
//	@WithMockUser(username = "chaunt", roles = {"USER"})
//	void testUpdateProfileUser() throws Exception{
//		// GIVEN
//		ObjectMapper objectMapper = new ObjectMapper();
//		String content = objectMapper.writeValueAsString(userUpdateProfileRequest);
//		// WHEN
//		Mockito.when(userService.updateProfileUser(ArgumentMatchers.anyString(), ArgumentMatchers.any(UserUpdateProfileRequest.class)))
//			.thenReturn(userResponse);
//		// THEN
//		mockMvc.perform(MockMvcRequestBuilders
//				.patch("/api/v1/user/{userId}", "662154df-ebe9-4732-900e-953bdbc22203")
//				.contentType(MediaType.APPLICATION_JSON_VALUE)
//				.content(content))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("code")
//						.value(1000)				
//		);
//	}

	// Fail tài khoản hoặc mật khẩu không đúng*******
//	@Test
//	@WithMockUser(username = "chaunt",password = "123456", roles= {"USER"})
//	void testCheckEmail() throws Exception {
//		HttpServletRequest request = null;
//		String email = "chauthanhnguyen838@gmail.com";
//		String response = userService.sendForgotPasswordEmail(email, request);
//		Mockito.when(userService.sendForgotPasswordEmail(ArgumentMatchers.anyString(), ArgumentMatchers.any(HttpServletRequest.class)))
//		.thenReturn(response);
//		mockMvc.perform(MockMvcRequestBuilders
//				.get("/api/v1/user/forgot-password")
//				.param("email", email)
//				.contentType(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
//				);
//	}
	
	// Mã xác thực không tồn tại
//	@Test
//	@WithMockUser(username = "chaunt", roles= {"USER"})
//	void testValidateToken() throws Exception {
//		String token = "token";
//		mockMvc.perform(MockMvcRequestBuilders
//				.get("/api/v1/user/forgot-password/verify-token")
//				.param("token", token)
//				.contentType(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
//				);
//	}
	
	// Mã xác thực không tồn tại
//	@Test
//	@WithMockUser(username = "chaunt", roles= {"USER"})
//	void testResetPassword() throws Exception {
//		ObjectMapper objectMapper = new ObjectMapper();
//		String content = objectMapper.writeValueAsString(resetPasswordRequest);
//		String token = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJIVFRDX1Nwb3J0Iiwic3ViIjoiNjYyMTU0ZGYtZWJlOS00NzMyLTkwMGUtOTUzYmRiYzIyMjAzIiwiZXhwIjoxNzIyODM4MTE4LCJpYXQiOjE3MjI4MzcyMTgsInNjb3BlIjoiUk9MRV9VU0VSIn0.EPltNSMpa_wD7UhqrCGRv3vgTi6RzwnBwb9UL-E4xdSSr3eWZdlO8veVha62dnMcjo0zw5BTwyKKcovB0rfyZpZ6nAIDAeJsh_ZJFBprhJR1U5rNh8O4is422CMrgZPy6qLri9j5hYFy0xpA_C1OEYLX9UbHorrk6YZCPmXZpZRiXFm6MHKZjESfnSlLM9kImNOOyHIvjmXydQ7Fg4cFEHre07VoF1Rbsl3GA3J7NHiM5WaNrugKfqU5u8teqUIxmidW090K8PY0sIwqzdfIFOwyOZOaUR3dRPqFhe5M8GQIKwiFauSi7ztkvL9Vo_jvDj-s5wHom2uffank6xO1hg";
//		
//		Mockito.when(userService.resetPassword(ArgumentMatchers.anyString(), ArgumentMatchers.any(ResetPasswordRequest.class)))
//		.thenReturn(changePasswordResponse);
//		
//		mockMvc.perform(MockMvcRequestBuilders
//				.post("/api/v1/user/forgot-password/reset-password")
//				.param("token", token)
//				.contentType(MediaType.APPLICATION_JSON_VALUE)
//				.content(content))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
//				);
//	}
	
//	@Test
//	@WithMockUser(username = "admin", roles = {"ADMIN"})
//	void testGetUser() throws Exception {
//		Mockito.when(userService.getUser(ArgumentMatchers.anyString())).thenReturn(userResponse);
//		
//		mockMvc.perform(MockMvcRequestBuilders
//				.get("/api/v1/user/{userId}", "662154df-ebe9-4732-900e-953bdbc22203")
//				.contentType(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("code")
//						.value(1000)				
//		);
//	}
	
//	@Test
//	@WithMockUser(username = "admin", roles = {"ADMIN"})
//	// Kiểm tra cập nhật thông tin
//	void updateUser() throws Exception {
//		// GIVEN
//		ObjectMapper objectMapper = new ObjectMapper();
//		String content = objectMapper.writeValueAsString(userUpdateRequest);
//		// WHEN
//		Mockito.when(
//				userService.updateUser(ArgumentMatchers.anyString(), ArgumentMatchers.any(UserUpdateRequest.class)))
//				.thenReturn(userResponse);
//		// THEN
//		mockMvc.perform(MockMvcRequestBuilders
//				.put("/api/v1/user/{userId}", "662154df-ebe9-4732-900e-953bdbc22203")
//				.contentType(MediaType.APPLICATION_JSON_VALUE).content(content))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000));
//	}
	
//	@Test
//	@WithMockUser(username = "admin", roles = {"ADMIN"})
//	void testAuthorizeUser() throws Exception {
//		ObjectMapper objectMapper = new ObjectMapper();
//		Set<String> roles = new HashSet<>();
//		roles.add("USER");
//		roles.add("ADMIN");
//	    
//	    authRequest = AuthorizeUserRequest.builder()
//	            .roles(roles)
//	            .build();
//		
//		String content = objectMapper.writeValueAsString(authRequest);
//		
//		Mockito.when(userService.authorizeUser(ArgumentMatchers.anyString(), ArgumentMatchers.any(AuthorizeUserRequest.class)))
//			.thenReturn(userResponse);	
//		
//		mockMvc.perform(MockMvcRequestBuilders
//				.put("/api/v1/user/authorize/{userId}", "662154df-ebe9-4732-900e-953bdbc22203")
//				.contentType(MediaType.APPLICATION_JSON_VALUE)
//				.content(content))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("code")
//						.value(1000)				
//		);
//	}
	
//	@Test
//	@WithMockUser(username = "admin", roles = {"ADMIN"})
//	void testDeleteUser() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders
//					.delete("/api/v1/user/{userId}", "662154df-ebe9-4732-900e-953bdbc22203")
//					.contentType(MediaType.APPLICATION_JSON_VALUE))
//					.andExpect(MockMvcResultMatchers.status().isOk())
//					.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
//				);
//	}
	
	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN"})
	void testGetUsers() throws Exception {
		// GIVEN
		ObjectMapper objectMapper = new ObjectMapper();
		int page = 0;
		int size = 5;
		// WHEN
		Mockito.when(userService.getUsers(page, size)).thenReturn(listUsers);
		// THEN
		mockMvc.perform(MockMvcRequestBuilders
					.get("/api/v1/user")
					.contentType(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
				);
	}
	
}
