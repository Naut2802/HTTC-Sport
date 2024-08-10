package com.fpoly.httc_sport.service;

import com.fpoly.httc_sport.dto.request.*;
import com.fpoly.httc_sport.dto.response.AuthenticationResponse;
import com.fpoly.httc_sport.entity.*;
import com.fpoly.httc_sport.event.OutboundCompleteEvent;
import com.fpoly.httc_sport.event.RegistrationCompleteEvent;
import com.fpoly.httc_sport.exception.AppException;
import com.fpoly.httc_sport.exception.ErrorCode;
import com.fpoly.httc_sport.mapper.RoleMapper;
import com.fpoly.httc_sport.mapper.UserMapper;
import com.fpoly.httc_sport.repository.*;
import com.fpoly.httc_sport.repository.httpclient.GoogleOutboundExchangeTokenClient;
import com.fpoly.httc_sport.repository.httpclient.GoogleOutboundUserInfoClient;
import com.fpoly.httc_sport.security.jwt.KeyUtils;
import com.fpoly.httc_sport.utils.Enum.RoleEnum;
import com.fpoly.httc_sport.utils.Enum.VipEnum;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
	UserRepository userRepository;
	RefreshTokenRepository refreshTokenRepository;
	RoleRepository roleRepository;
	VipRepository vipRepository;
	VerificationTokenRepository verificationTokenRepository;
	GoogleOutboundExchangeTokenClient googleOutboundExchangeTokenClient;
	GoogleOutboundUserInfoClient googleOutboundUserInfoClient;
	UserMapper userMapper;
	RoleMapper roleMapper;
	PasswordEncoder passwordEncoder;
	ApplicationEventPublisher publisher;
	JwtService jwtService;
	MailerService mailerService;
	ChatService chatService;
	KeyUtils keyUtils;
	
	@NonFinal
	@Value("${jwt.refresh-token-valid-duration}")
	long REFRESH_TOKEN_VALID_DURATION;
	
	@NonFinal
	@Value("${oauth2.google.client-id}")
	String GOOGLE_CLIENT_ID;
	
	@NonFinal
	@Value("${oauth2.google.client-secret}")
	String GOOGLE_CLIENT_SECRET;
	
	@NonFinal
	@Value("${oauth2.google.redirect-uri}")
	String REDIRECT_URI;
	
	@NonFinal
	final String GRANT_TYPE = "authorization_code";
	
	public String register(RegisterRequest request, HttpServletRequest httpRequest) {
		if(userRepository.existsByUsername(request.getUsername()))
			throw new AppException(ErrorCode.USER_EXISTED);
		if(userRepository.existsByEmail(request.getEmail()))
			throw new AppException(ErrorCode.EMAIL_EXISTED);
		
		var user = userMapper.toUser(request);
		
		user.setPassword(encodePassword(user.getUsername(), user.getPassword()));
		user.setIsEnabled(false);
		
		var role = roleRepository.findByRoleName(RoleEnum.USER).orElseThrow(() ->
				new AppException(ErrorCode.ROLE_NOT_EXISTED));
		var vip = vipRepository.findByLevel(VipEnum.VIP_0).orElseThrow(() ->
				new AppException(ErrorCode.VIP_NOT_EXISTED));
		
		
		user.setRoles(List.of(role));
		user.setVip(vip);
		userRepository.save(user);
		String url = generateUrl(httpRequest);
		publisher.publishEvent(new RegistrationCompleteEvent(user, url));
		
		return "Đăng ký tài khoản thành công, vui lòng xác thực tài khoản thông qua email đã đăng ký";
	}
	
	public String validateEmailToken(String token) {
		var verificationToken = verificationTokenRepository.findByToken(token).orElseThrow(() ->
				new AppException(ErrorCode.VERIFICATION_TOKEN_NOT_FOUND));
		
		var user = verificationToken.getUser();
		if (user.getIsEnabled())
			throw new AppException(ErrorCode.USER_ENABLED);
		
		if (verificationToken.getExpiryTime().before(Date.from(Instant.now())))
			return "Invalid, token expired";
		
		user.setIsEnabled(true);
		verificationTokenRepository.delete(verificationToken);
		userRepository.save(user);
		return "Valid";
	}
	
	public String resendVerificationToken(String oldToken, HttpServletRequest request) {
		var newVerificationToken = generateNewVerificationToken(oldToken);
		var user = newVerificationToken.getUser();
		String url = generateUrl(request)+"/verify-email?token="+newVerificationToken.getToken();
		
		MailInfo mailInfo = MailInfo.builder()
				.to(user.getEmail())
				.subject("Email xác thực tài khoản")
				.body(mailerService.generateVerificationBody(url))
				.build();
		
		mailerService.queue(mailInfo);
		log.info(url);
		
		return "Đã gửi lại email xác thực tài khoản, vui lòng kiểm tra email";
	}
	
	public AuthenticationResponse authenticate(LoginRequest request, HttpServletResponse response) throws NoSuchAlgorithmException {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
		var user = userRepository.findByUsername(request.getUsername())
				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
		
		if (!user.getIsEnabled())
			throw new AppException(ErrorCode.USER_NOT_EXISTED);
		
		if (!passwordEncoder.matches(request.getUsername() + request.getPassword(), user.getPassword()))
			throw new AppException(ErrorCode.USER_NOT_EXISTED);
		
		KeyPair keyPair = keyUtils.generateKeyPair();
		var accessToken = jwtService.generateAccessToken(user, keyPair);
		String jti = accessToken.substring(accessToken.length()-10);
		var refreshToken = jwtService.generateRefreshToken(user, jti, keyPair);
		String publicKey = keyUtils.exchangeRSAPublicKeyToString((RSAPublicKey) keyPair.getPublic());
		
		saveRefreshToken(user, refreshToken, publicKey);
		
		createRefreshTokenCookie(response, jti);
		
		var chatRooms = chatService.findById(user.getUsername().equals("admin") ? "admin" : user.getId());
		
		return AuthenticationResponse.builder()
				.accessToken(accessToken)
				.userId(user.getId())
				.authenticated(true)
				.chatRooms(chatRooms)
				.roles(new HashSet<>(user.getRoles().stream().map(roleMapper::toRoleResponse).toList()))
				.build();
	}
	
	public AuthenticationResponse googleOutboundAuthenticate(String code, HttpServletResponse response) throws NoSuchAlgorithmException {
		var tokenExchanged = googleOutboundExchangeTokenClient.exchangeToken(GoogleExchangeTokenRequest.builder()
						.code(code)
						.clientId(GOOGLE_CLIENT_ID)
						.clientSecret(GOOGLE_CLIENT_SECRET)
						.redirectUri(REDIRECT_URI)
						.grantType(GRANT_TYPE)
				.build());
		
		log.info("Google token exchanged {}", tokenExchanged);
		
		var userInfo = googleOutboundUserInfoClient.getUserInfo("json", tokenExchanged.getAccessToken());
		
		var role = roleRepository.findByRoleName(RoleEnum.USER).orElseThrow(() ->
				new AppException(ErrorCode.ROLE_NOT_EXISTED));
		var vip = vipRepository.findByLevel(VipEnum.VIP_0).orElseThrow(() ->
				new AppException(ErrorCode.VIP_NOT_EXISTED));
		
		String password = generateRandomPassword();
		Optional<User> _user = userRepository.findByEmail(userInfo.getEmail());
		User user = null;
		
		if (_user.isEmpty()) {
			user = userRepository.save(User.builder()
					.username(userInfo.getEmail())
					.password(passwordEncoder.encode(userInfo.getEmail() + password))
					.email(userInfo.getEmail())
					.firstName(userInfo.getName())
					.lastName(userInfo.getFamilyName())
					.isEnabled(true)
					.roles(List.of(role))
					.vip(vip)
					.build());
			publisher.publishEvent(new OutboundCompleteEvent(user, password));
		} else
			user = _user.get();
		
		if (!user.getIsEnabled())
			user.setIsEnabled(true);
		
		KeyPair keyPair = keyUtils.generateKeyPair();
		var accessToken = jwtService.generateAccessToken(user, keyPair);
		String jti = accessToken.substring(accessToken.length()-10);
		var refreshToken = jwtService.generateRefreshToken(user, jti, keyPair);
		String publicKey = keyUtils.exchangeRSAPublicKeyToString((RSAPublicKey) keyPair.getPublic());
		
		saveRefreshToken(user, refreshToken, publicKey);
		
		createRefreshTokenCookie(response, jti);
		
		return AuthenticationResponse.builder()
				.accessToken(accessToken)
				.userId(user.getId())
				.authenticated(true)
				.roles(new HashSet<>(user.getRoles().stream().map(roleMapper::toRoleResponse).toList()))
				.build();
	}
	
	@Transactional
	public AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response, RefreshRequest refreshRequest) throws NoSuchAlgorithmException {
		User user = userRepository.findById(refreshRequest.getUserId())
				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
		
		var rti = getRefreshTokenFromCookies(request);
		
		if (rti == null)
			throw new AppException(ErrorCode.UNAUTHENTICATED);
		
		var refreshTokenFromRepo = refreshTokenRepository.findById(rti)
				.orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
		
		if (refreshTokenFromRepo.getExpiryTime().toInstant().isBefore(Instant.now()))
			throw new AppException(ErrorCode.UNAUTHENTICATED);
		
		refreshTokenRepository.deleteById(refreshTokenFromRepo.getId());
		
		KeyPair keyPair = keyUtils.generateKeyPair();
		var accessToken = jwtService.generateAccessToken(user, keyPair);
		String jti = accessToken.substring(accessToken.length()-10);
		var refreshToken = jwtService.generateRefreshToken(user, jti, keyPair);
		String publicKey = keyUtils.exchangeRSAPublicKeyToString((RSAPublicKey) keyPair.getPublic());
		
		saveRefreshToken(user, refreshToken, publicKey);
		
		createRefreshTokenCookie(response, jti);
		
		return AuthenticationResponse.builder()
				.accessToken(accessToken)
				.userId(user.getId())
				.authenticated(true)
				.roles(new HashSet<>(user.getRoles().stream().map(roleMapper::toRoleResponse).toList()))
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
	
	private void createRefreshTokenCookie(HttpServletResponse response, String jti) {
		Cookie refreshTokenCookie = new Cookie("rti", jti);
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setSecure(true);
		refreshTokenCookie.setPath("/");
		refreshTokenCookie.setMaxAge((int) REFRESH_TOKEN_VALID_DURATION + (60 * 60 * 48)); // in seconds
		response.addCookie(refreshTokenCookie);
	}
	
	private String getRefreshTokenFromCookies(HttpServletRequest request) {
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if ("rti".equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
	
	private String encodePassword(String username, String password) {
		return passwordEncoder.encode(username + password);
	}
	
	public String generateUrl(HttpServletRequest request) {
		return "http://"+request.getServerName()+":"+request.getServerPort()+"/api/auth/sign-up";
	}
	
	public void saveVerificationToken(User user, String token) {
		VerificationToken verificationToken = VerificationToken.builder()
				.token(token)
				.expiryTime(Date.from(Instant.now().plus(15, ChronoUnit.MINUTES)))
				.user(user)
				.build();
		verificationTokenRepository.save(verificationToken);
	}
	
	private VerificationToken generateNewVerificationToken(String oldToken) {
		var verificationToken = verificationTokenRepository.findByToken(oldToken).orElseThrow(() ->
				new AppException(ErrorCode.VERIFICATION_TOKEN_NOT_FOUND));
		
		verificationToken.setToken(UUID.randomUUID().toString());
		verificationToken.setExpiryTime(Date.from(Instant.now().plus(15, ChronoUnit.MINUTES)));
		verificationTokenRepository.save(verificationToken);
		
		return verificationToken;
	}
	
	private String generateRandomPassword() {
		Random random = new Random();
		return String.valueOf(random.nextInt(100_000, 999_999));
	}
}
