package com.fpoly.httc_sport.service;

import com.fpoly.httc_sport.dto.request.*;
import com.fpoly.httc_sport.dto.response.ChangePasswordResponse;
import com.fpoly.httc_sport.dto.response.UserResponse;
import com.fpoly.httc_sport.entity.*;
import com.fpoly.httc_sport.event.ForgotPasswordEvent;
import com.fpoly.httc_sport.exception.AppException;
import com.fpoly.httc_sport.exception.ErrorCode;
import com.fpoly.httc_sport.mapper.UserMapper;
import com.fpoly.httc_sport.repository.ForgotPasswordTokenRepository;
import com.fpoly.httc_sport.repository.RoleRepository;
import com.fpoly.httc_sport.repository.UserRepository;
import com.fpoly.httc_sport.utils.Enum.RoleEnum;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

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
	
	ChatService chatService;
	
	public ChangePasswordResponse changePassword(String userId, ChangePasswordRequest request) {
		var user = userRepository.findById(userId).orElseThrow(() ->
				new AppException(ErrorCode.USER_NOT_EXISTED));
		
		var context = SecurityContextHolder.getContext();
		String name = context.getAuthentication().getName();
		if (!user.getUsername().equals(name))
			throw new AppException(ErrorCode.USER_NOT_EXISTED);
		
		if (!passwordEncoder.matches(user.getUsername() + request.getCurrentPassword(), user.getPassword()))
			throw new AppException(ErrorCode.WRONG_PASSWORD);
		
		if (!request.getNewPassword().equals(request.getConfirmationPassword()))
			throw new AppException(ErrorCode.WRONG_CONFIRMATION_PASSWORD);
		
		String message = "Thay đổi mật khẩu thất bại, mật khẩu mới không được giống mật khẩu cũ";
		boolean flag = false;
		if (!passwordEncoder.matches(user.getUsername() + request.getNewPassword(), user.getPassword())) {
			user.setPassword(encodePassword(user.getUsername(), request.getNewPassword()));
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
		var chatRooms = chatService.findById(user.getUsername().equals("admin") ? "admin" : user.getId());
		
		var response = userMapper.toUserResponse(user);
		response.setChatRooms(chatRooms);
		
		return response;
	}
	
	public List<ChatMessage> getChatMessagesByRoom(int roomId) {
		var context = SecurityContextHolder.getContext();
		var user = userRepository.findByUsername(context.getAuthentication().getName()).orElseThrow(
				() -> new AppException(ErrorCode.UNAUTHENTICATED)
		);
		
		var chatRooms = chatService.findById(user.getUsername().equals("admin") ? "admin" : user.getId());
		var roomFounded = chatRooms.stream()
				.filter(chatRoom -> chatRoom.getId() == roomId).findFirst().orElse(null);
		
		if (roomFounded == null)
			throw new AppException(ErrorCode.CHAT_ROOM_NOT_EXIST);
		
		return roomFounded.getMessages();
	}
	
	public UserResponse updateProfileUser(String userId, UserUpdateProfileRequest request) {
		var user = userRepository.findById(userId).orElseThrow(()
				-> new AppException(ErrorCode.USER_NOT_EXISTED));
		
		var context = SecurityContextHolder.getContext();
		String name = context.getAuthentication().getName();
		if (!user.getUsername().equals(name))
			throw new AppException(ErrorCode.USER_NOT_EXISTED);
		
		userMapper.updateProfileUser(user, request);
		return userMapper.toUserResponse(userRepository.save(user));
	}
	
	public String sendForgotPasswordEmail(String email, HttpServletRequest request) {
		var user = userRepository.findByEmail(email).orElseThrow(() ->
				new AppException(ErrorCode.EMAIL_NOT_EXISTED));
		
		Optional<ForgotPasswordToken> token = forgotPasswordTokenRepository.findByUser(user);
		
		token.ifPresent(forgotPasswordTokenRepository::delete);
		
		if (!user.getIsEnabled())
			throw new AppException(ErrorCode.USER_NOT_EXISTED);
		
		String url = generateUrl(request);
		
		publisher.publishEvent(new ForgotPasswordEvent(user, url));
		
		return "Đã gửi yêu cầu reset mật khẩu thông qua email đăng ký, vui lòng kiểm tra email";
	}
	
	public String validateForgotPasswordToken(String token) {
		var forgotPasswordToken = forgotPasswordTokenRepository.findByToken(token).orElseThrow(() ->
				new AppException(ErrorCode.FORGOT_PASSWORD_TOKEN_NOT_FOUND));
		
		if (forgotPasswordToken.getExpiryTime().before(Date.from(Instant.now()))) {
			forgotPasswordTokenRepository.delete(forgotPasswordToken);
			return "Invalid, token expired";
		}
		
		return "Link xác thực hợp lệ";
	}
	
	public ChangePasswordResponse resetPassword(String token, ResetPasswordRequest request) {
		var forgotPasswordToken = forgotPasswordTokenRepository.findByToken(token).orElseThrow(() ->
				new AppException(ErrorCode.FORGOT_PASSWORD_TOKEN_NOT_FOUND));
		var user = forgotPasswordToken.getUser();
		
		if (!request.getNewPassword().equals(request.getConfirmationPassword()))
			throw new AppException(ErrorCode.WRONG_CONFIRMATION_PASSWORD);
		
		String message = "Reset mật khẩu thất bại, mật khẩu mới không được giống mật khẩu cũ";
		boolean flag = false;
		if (!passwordEncoder.matches(user.getUsername() + request.getNewPassword(), user.getPassword())) {
			user.setPassword(encodePassword(user.getUsername(), request.getNewPassword()));
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
	
	public UserResponse updateUser(String userId, UserUpdateRequest request) {
		var user = userRepository.findById(userId).orElseThrow(()
				-> new AppException(ErrorCode.USER_NOT_EXISTED));
		
		userMapper.updateUser(user, request);
		return userMapper.toUserResponse(userRepository.save(user));
	}
	
	public UserResponse authorizeUser(String userId, AuthorizeUserRequest request) {
		var user = userRepository.findById(userId).orElseThrow(()
				-> new AppException(ErrorCode.USER_NOT_EXISTED));
		
		List<Role> roles = roleRepository.findByRoleNameIn(request.getRoles().stream().map(RoleEnum::valueOf).toList());
		
		user.setRoles(roles);
		return userMapper.toUserResponse(userRepository.save(user));
	}
	
	public UserResponse getUser(String userId) {
		return userMapper.toUserResponse(userRepository.findById(userId).orElseThrow(()
				-> new AppException(ErrorCode.USER_NOT_EXISTED)));
	}
	
	public List<UserResponse> getUsers(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return userRepository.findAll(pageable).stream().map(userMapper::toUserResponse).toList();
	}
	
	public String deleteUser(String userId) {
		var user = userRepository.findById(userId).orElseThrow(()
				-> new AppException(ErrorCode.USER_NOT_EXISTED));
		
		if (!user.getIsEnabled())
			return "Tài khoản này đã bị hủy kích hoạt";
		
		user.setIsEnabled(false);
		userRepository.save(user);
		
		return "Hủy kích hoạt tài khoản thành công";
	}
	
	public String activeUser(String userId) {
		var user = userRepository.findById(userId).orElseThrow(()
				-> new AppException(ErrorCode.USER_NOT_EXISTED));
		
		if (user.getIsEnabled())
			return "Tài khoản này đang hoạt động";
		
		user.setIsEnabled(true);
		userRepository.save(user);
		
		return "Kích hoạt tài khoản thành công";
	}
	
	public void saveForgotPasswordToken(User user, String token) {
		var forgotPasswordToken = ForgotPasswordToken.builder()
				.token(token)
				.expiryTime(Date.from(Instant.now().plus(2, ChronoUnit.MINUTES)))
				.user(user)
				.build();
		forgotPasswordTokenRepository.save(forgotPasswordToken);
	}
	
	private String generateUrl(HttpServletRequest request) {
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
