package com.fpoly.httc_sport.service;

import com.fpoly.httc_sport.dto.request.LoginRequest;
import com.fpoly.httc_sport.dto.request.RefreshRequest;
import com.fpoly.httc_sport.dto.request.RegisterRequest;
import com.fpoly.httc_sport.dto.response.AuthenticationResponse;
import com.fpoly.httc_sport.dto.response.UserResponse;
import com.fpoly.httc_sport.entity.RefreshTokenWhiteList;
import com.fpoly.httc_sport.entity.User;
import com.fpoly.httc_sport.exception.AppException;
import com.fpoly.httc_sport.exception.ErrorCode;
import com.fpoly.httc_sport.mapper.UserMapper;
import com.fpoly.httc_sport.repository.RefreshTokenRepository;
import com.fpoly.httc_sport.repository.RoleRepository;
import com.fpoly.httc_sport.repository.UserRepository;
import com.fpoly.httc_sport.security.jwt.KeyUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
	UserRepository userRepository;
	RefreshTokenRepository refreshTokenRepository;
	JwtService jwtService;
	UserMapper userMapper;
	PasswordEncoder passwordEncoder;
	RoleRepository roleRepository;
	KeyUtils keyUtils;
	
	@NonFinal
	@Value("${jwt.refresh-token-valid-duration}")
	long REFRESH_TOKEN_VALID_DURATION;
	
	public UserResponse register(RegisterRequest request) {
		if(userRepository.existsByUsername(request.username()))
			throw new AppException(ErrorCode.USER_EXISTED);
		if(userRepository.existsByEmail(request.email()))
			throw new AppException(ErrorCode.USER_EXISTED);
		
		var user = userMapper.toUser(request);
		
		user.setPassword(encodePassword(user.getUsername(), user.getPassword()));
		
		var role = roleRepository.findByRoleName("USER").orElseThrow(() ->
				new AppException(ErrorCode.ROLE_NOT_EXISTED));
		
		user.setRoles(new HashSet<>(List.of(role)));
		
		return userMapper.toUserResponse(userRepository.save(user));
	}
	
	public AuthenticationResponse authenticate(LoginRequest request, HttpServletResponse response) throws NoSuchAlgorithmException {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
		var user = userRepository
				.findByUsername(request.username())
				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
		
		boolean authenticated = passwordEncoder.matches(request.username() + request.password(), user.getPassword());
		
		if (!authenticated) throw new AppException(ErrorCode.USER_NOT_EXISTED);
		
		KeyPair keyPair = keyUtils.generateKeyPair();
		var accessToken = jwtService.generateAccessToken(user, keyPair);
		String jti = accessToken.substring(accessToken.length()-10);
		var refreshToken = jwtService.generateRefreshToken(user, jti, keyPair);
		String publicKey = keyUtils.exchangeRSAPublicKeyToString((RSAPublicKey) keyPair.getPublic());
		
		saveRefreshToken(user, refreshToken, publicKey);
		
		createRefreshTokenCookie(response, refreshToken);
		
		return AuthenticationResponse.builder()
				.accessToken(accessToken)
				.userId(user.getId())
				.authenticated(true)
				.build();
	}
	
	@Transactional
	public AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response, RefreshRequest refreshRequest) throws NoSuchAlgorithmException {
		User user = userRepository.findById(refreshRequest.userId())
				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
		
		var refreshTokenFromCookies = getRefreshTokenFromCookies(request);
		
		var refreshTokenFromRepo = refreshTokenRepository.findByToken(refreshTokenFromCookies)
				.orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
		
		if (refreshTokenFromRepo.getExpiryTime().toInstant().isBefore(Instant.now()))
			throw new AppException(ErrorCode.UNAUTHENTICATED);
		
		refreshTokenRepository.deleteByToken(refreshTokenFromCookies);
		
		KeyPair keyPair = keyUtils.generateKeyPair();
		var accessToken = jwtService.generateAccessToken(user, keyPair);
		String jti = accessToken.substring(accessToken.length()-10);
		var refreshToken = jwtService.generateRefreshToken(user, jti, keyPair);
		String publicKey = keyUtils.exchangeRSAPublicKeyToString((RSAPublicKey) keyPair.getPublic());
		
		saveRefreshToken(user, refreshToken, publicKey);
		
		createRefreshTokenCookie(response, refreshToken);
		
		return AuthenticationResponse.builder()
				.accessToken(accessToken)
				.userId(user.getId())
				.authenticated(true)
				.build();
	}
	
	private void saveRefreshToken(User user, Jwt refreshToken, String publicKey) {
		refreshTokenRepository.save(RefreshTokenWhiteList.builder()
				.id(refreshToken.getId())
				.user(user)
				.token(refreshToken.getTokenValue())
				.publicKey(publicKey)
				.expiryTime(Date.from(Objects.requireNonNull(refreshToken.getExpiresAt())))
				.build());
	}
	
	private void createRefreshTokenCookie(HttpServletResponse response, Jwt refreshToken) {
		Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken.getTokenValue());
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setSecure(true);
		refreshTokenCookie.setMaxAge((int) REFRESH_TOKEN_VALID_DURATION + (60 * 60 * 48)); // in seconds
		response.addCookie(refreshTokenCookie);
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
	
	private String encodePassword(String username, String password) {
		return passwordEncoder.encode(username + password);
	}
}
