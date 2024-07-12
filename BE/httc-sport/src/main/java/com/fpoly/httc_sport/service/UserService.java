package com.fpoly.httc_sport.service;

import com.fpoly.httc_sport.dto.request.AuthorizeUserRequest;
import com.fpoly.httc_sport.dto.request.ChangePasswordRequest;
import com.fpoly.httc_sport.dto.request.ResetPasswordRequest;
import com.fpoly.httc_sport.dto.request.UserUpdateRequest;
import com.fpoly.httc_sport.dto.response.ChangePasswordResponse;
import com.fpoly.httc_sport.dto.response.UserResponse;
import com.fpoly.httc_sport.entity.ForgotPasswordToken;
import com.fpoly.httc_sport.entity.Role;
import com.fpoly.httc_sport.entity.User;
import com.fpoly.httc_sport.entity.VerificationToken;
import com.fpoly.httc_sport.event.ForgotPasswordEvent;
import com.fpoly.httc_sport.exception.AppException;
import com.fpoly.httc_sport.exception.ErrorCode;
import com.fpoly.httc_sport.mapper.UserMapper;
import com.fpoly.httc_sport.repository.ForgotPasswordTokenRepository;
import com.fpoly.httc_sport.repository.RoleRepository;
import com.fpoly.httc_sport.repository.UserRepository;
import com.fpoly.httc_sport.repository.VerificationTokenRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
	PasswordEncoder passwordEncoder;
	UserRepository userRepository;
	RoleRepository roleRepository;
	ForgotPasswordTokenRepository forgotPasswordTokenRepository;
	ApplicationEventPublisher publisher;
	UserMapper userMapper;
	
	public ChangePasswordResponse changePassword(String userId, ChangePasswordRequest request) {
		var user = userRepository.findById(userId).orElseThrow(() ->
				new AppException(ErrorCode.USER_NOT_EXISTED));
		
		var context = SecurityContextHolder.getContext();
		String name = context.getAuthentication().getName();
		if (!user.getUsername().equals(name))
			throw new AppException(ErrorCode.USER_NOT_EXISTED);
		
		if (!passwordEncoder.matches(user.getUsername() + request.currentPassword(), user.getPassword()))
			throw new AppException(ErrorCode.WRONG_PASSWORD);
		
		if (!request.newPassword().equals(request.confirmationPassword()))
			throw new AppException(ErrorCode.WRONG_CONFIRMATION_PASSWORD);
		
		String message = "Thay đổi mật khẩu thất bại, mật khẩu mới không được giống mật khẩu cũ";
		boolean flag = false;
		if (!passwordEncoder.matches(user.getUsername() + request.newPassword(), user.getPassword())) {
			user.setPassword(encodePassword(user.getUsername(), request.newPassword()));
			userRepository.save(user);
			message = "Thay đổi mật khẩu thành công";
			flag = true;
		}
		
		return ChangePasswordResponse.builder()
				.username(user.getUsername())
				.message(message)
				.isChanged(flag)
				.build();
	}
	
	@PostAuthorize("returnObject.username == authentication.name")
	public UserResponse getMyInfo() {
		var context = SecurityContextHolder.getContext();
		String name = context.getAuthentication().getName();
		User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
		
		return userMapper.toUserResponse(user);
	}
	
	public UserResponse updateUser(String userId, UserUpdateRequest request) {
		var user = userRepository.findById(userId).orElseThrow(()
				-> new AppException(ErrorCode.USER_NOT_EXISTED));
		
		var context = SecurityContextHolder.getContext();
		String name = context.getAuthentication().getName();
		if (!user.getUsername().equals(name))
			throw new AppException(ErrorCode.USER_NOT_EXISTED);
		
		userMapper.updateUser(user, request);
		user.setPassword(encodePassword(user.getUsername(), user.getPassword()));
		return userMapper.toUserResponse(userRepository.save(user));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	public UserResponse authorizeUser(String userId, AuthorizeUserRequest request) {
		var user = userRepository.findById(userId).orElseThrow(()
				-> new AppException(ErrorCode.USER_NOT_EXISTED));
		
		List<Role> roles = roleRepository.findAllByRoleNameIn(request.roles());
		
		user.setRoles(new HashSet<>(roles));
		return userMapper.toUserResponse(userRepository.save(user));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	public UserResponse getUser(String userId) {
		return userMapper.toUserResponse(userRepository.findById(userId).orElseThrow(()
				-> new AppException(ErrorCode.USER_NOT_EXISTED)));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	public List<UserResponse> getUsers() {
		return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteUser(String userId) {
		var user = userRepository.findById(userId).orElseThrow(()
				-> new AppException(ErrorCode.USER_NOT_EXISTED));
		
		user.setIsEnabled(false);
		userRepository.save(user);
	}
	
	public String sendForgotPasswordEmail(String email, HttpServletRequest request) {
		var user = userRepository.findByEmail(email).orElseThrow(() ->
				new AppException(ErrorCode.USER_NOT_EXISTED));
		
		if (!user.getIsEnabled())
			throw new AppException(ErrorCode.USER_NOT_EXISTED);
		
		String url = generateUrl(request);
		
		publisher.publishEvent(new ForgotPasswordEvent(user, url));
		
		return "Đã gửi yêu cầu reset mật khẩu thông qua email đăng ký, vui lòng kiểm tra email";
	}
	
	public String validateForgotPasswordToken(String token) {
		var forgotPasswordToken = forgotPasswordTokenRepository.findByToken(token).orElseThrow(() ->
				new AppException(ErrorCode.FORGOT_PASSWORD_TOKEN_NOT_FOUND));
		
		if (forgotPasswordToken.getExpiryTime().before(Date.from(Instant.now())))
			return "Invalid, token expired";
		
		return "Link xác thực hợp lệ";
	}
	
	public ChangePasswordResponse resetPassword(String token, ResetPasswordRequest request) {
		var forgotPasswordToken = forgotPasswordTokenRepository.findByToken(token).orElseThrow(() ->
				new AppException(ErrorCode.FORGOT_PASSWORD_TOKEN_NOT_FOUND));
		var user = forgotPasswordToken.getUser();
		
		if (!request.newPassword().equals(request.confirmationPassword()))
			throw new AppException(ErrorCode.WRONG_CONFIRMATION_PASSWORD);
		
		String message = "Reset mật khẩu thất bại, mật khẩu mới không được giống mật khẩu cũ";
		boolean flag = false;
		if (!passwordEncoder.matches(user.getUsername() + request.newPassword(), user.getPassword())) {
			user.setPassword(encodePassword(user.getUsername(), request.newPassword()));
			userRepository.save(user);
			forgotPasswordTokenRepository.delete(forgotPasswordToken);
			message = "Reset mật khẩu thành công";
			flag = true;
		}
		
		return ChangePasswordResponse.builder()
				.username(user.getUsername())
				.message(message)
				.isChanged(flag)
				.build();
	}
	
	public void saveForgotPasswordToken(User user, String token) {
		var forgotPasswordToken = ForgotPasswordToken.builder()
				.token(token)
				.expiryTime(Date.from(Instant.now().plus(2, ChronoUnit.MINUTES)))
				.user(user)
				.build();
		forgotPasswordTokenRepository.save(forgotPasswordToken);
	}
	
	public String generateUrl(HttpServletRequest request) {
		return "http://"+request.getServerName()+":"+request.getServerPort()+"/api/v1/user/forgot-password";
	}
	
	private String encodePassword(String username, String password) {
		return passwordEncoder.encode(username + password);
	}
	
	private String getRefreshTokenFromCookies(HttpServletRequest request) {
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if ("refresh_token".equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
//	@Autowired
//	private com.fpoly.repository.HoaDonRepository hoadonRepository;
//	@Autowired
//	private VipRepo VipRepository;
//	@Autowired
//	private ThongTinDatRepository thongtinRepository;
//	private int time = 0;
//
//	public List<User> getAllUsers() {
//		List<User> users = (List<User>) userRepository.findUserByRolesRoleNameLike("USER");
//
//		return users;
//	}
//
//	public Boolean delete(String username) {
//		User _userFind = userRepository.findById(username).orElse(null);
//		if(_userFind != null) {
//			_userFind.setStatus(false);
//			userRepository.save(_userFind);
//			return true;
//		}
//
//		return false;
//	}
//
//	public List<Vip> findAll(){
//		return VipRepository.findAll();
//	}
//
//	public List<ThongTinDatSan> findByUser(String username){
//		return (List<ThongTinDatSan>) thongtinRepository.findByUserUsername(username);
//	}

//	@Scheduled(fixedDelay = 1000, initialDelay = 1000)
//	public void checkTimeOtp() {
//		time++;
//		if(time == 20) {
//
//		}
//	}
}
