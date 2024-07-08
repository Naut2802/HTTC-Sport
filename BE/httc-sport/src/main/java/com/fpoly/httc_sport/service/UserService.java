package com.fpoly.httc_sport.service;

import com.fpoly.httc_sport.dto.request.AuthorizeUserRequest;
import com.fpoly.httc_sport.dto.request.ChangePasswordRequest;
import com.fpoly.httc_sport.dto.request.UserUpdateRequest;
import com.fpoly.httc_sport.dto.response.ChangePasswordResponse;
import com.fpoly.httc_sport.dto.response.UserResponse;
import com.fpoly.httc_sport.entity.Role;
import com.fpoly.httc_sport.entity.User;
import com.fpoly.httc_sport.exception.AppException;
import com.fpoly.httc_sport.exception.ErrorCode;
import com.fpoly.httc_sport.mapper.UserMapper;
import com.fpoly.httc_sport.repository.RoleRepository;
import com.fpoly.httc_sport.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
	PasswordEncoder passwordEncoder;
	UserRepository userRepository;
	RoleRepository roleRepository;
	UserMapper userMapper;
	int time = 0;
	
	@PostAuthorize("returnObject.username == authentication.name")
	public ChangePasswordResponse changePassword(String userId, ChangePasswordRequest request) {
		var user = userRepository.findById(userId).orElseThrow(() ->
				new AppException(ErrorCode.USER_NOT_EXISTED));
		
		if (!passwordEncoder.matches(user.getUsername() + request.currentPassword(), user.getPassword()))
			throw new AppException(ErrorCode.WRONG_PASSWORD);
		
		if (!request.newPassword().equals(request.confirmationPassword()))
			throw new AppException(ErrorCode.WRONG_CONFIRMATION_PASSWORD);
		
		user.setPassword(encodePassword(user.getUsername(), request.newPassword()));
		
		userRepository.save(user);
		return ChangePasswordResponse.builder()
				.username(user.getUsername())
				.message("Thay đổi mật khẩu thành công")
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
		
		user.setStatus(false);
		userRepository.save(user);
	}
	
	private String encodePassword(String username, String password) {
		return passwordEncoder.encode(username + password);
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
