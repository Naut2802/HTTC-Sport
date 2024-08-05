package com.fpoly.httc_sport.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fpoly.httc_sport.dto.request.ChangePasswordRequest;
import com.fpoly.httc_sport.dto.request.UserUpdateRequest;
import com.fpoly.httc_sport.dto.response.ChangePasswordResponse;
import com.fpoly.httc_sport.dto.response.UserResponse;
import com.fpoly.httc_sport.entity.User;
import com.fpoly.httc_sport.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class UserServiceTest {
	@Autowired
	private UserService userService;
	
	@MockBean
	private UserRepository userRepository;
	
	private UserUpdateRequest request;
	private UserResponse response;
	private ChangePasswordResponse changePasswordResponse;
	private ChangePasswordRequest changepPasswordRequest;
	private User user;
	
	@BeforeEach
	void initData() {
		request = UserUpdateRequest.builder()
				.email("chauthanhnguyen838@gmail.com")
				.firstName("Châu")
				.lastName("Nguyễn")
				.phoneNumber("0921372381")
				.build();
		
		response = UserResponse.builder()
				.id("1")
				.username("chaunt")
				.email("chauthanhnguyen838@gmail.com")
				.firstName("Châu")
				.lastName("Nguyễn")
				.phoneNumber("0921372381")
				.isEnabled(true)
				.build();
		
		user = User.builder()
				.id("1")
				.username("chaunt")
				.password("123")
				.email("chauthanhnguyen838@gmail.com")
				.firstName("Châu")
				.lastName("Nguyễn")
				.phoneNumber("0921372381")
				.isEnabled(true)
				.build();
	}
	
	@Test
	void testChangePasswordSuccess() {
	    // GIVEN
	    ChangePasswordRequest changePasswordRequest = ChangePasswordRequest.builder()
	            .currentPassword("123")
	            .newPassword("321")
	            .confirmationPassword("321")
	            .build();

	    // Cập nhật mật khẩu người dùng để khớp với mật khẩu trong yêu cầu
	    user.setPassword("321");  // Điều này mô phỏng mật khẩu được mã hóa

	    // Mocking the behavior of userRepository to return the user
	    when(userRepository.findById("1")).thenReturn(Optional.of(user));
	    // Simulate the password matching
	    when(userRepository.save(user)).thenAnswer(invocation -> {
	        User savedUser = invocation.getArgument(0);
	        savedUser.setPassword("chauntnewPassword");
	        return savedUser;
	    });

	    // WHEN
	    ChangePasswordResponse changePasswordResponse = userService.changePassword("1", changePasswordRequest);

	    // THEN
	    assertTrue(changePasswordResponse.isChanged());
	    assertEquals("Thay đổi mật khẩu thành công", changePasswordResponse.getMessage());
	    verify(userRepository, times(1)).findById("1");
	    verify(userRepository, times(1)).save(user);

	    // Validate the password has been changed
	    assertEquals("321", user.getPassword());
	}

}
