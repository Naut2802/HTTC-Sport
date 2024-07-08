package com.fpoly.httc_sport.security.jwt;

import com.fpoly.httc_sport.exception.AppException;
import com.fpoly.httc_sport.exception.ErrorCode;
import com.fpoly.httc_sport.repository.UserRepository;
import com.fpoly.httc_sport.security.user.CustomUserDetails;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtUtils {
	UserRepository userRepository;
	
	public String getUserId(Jwt jwt) {
		return jwt.getSubject();
	}
	
	public boolean isTokenValid(Jwt jwt) {
		boolean isTokenExpired = getIfTokenIsExpired(jwt);
		boolean isUserExist = userRepository.existsById(getUserId(jwt));
		
		if (isTokenExpired) throw new AppException(ErrorCode.TOKEN_EXPIRED);
		return isUserExist;
	}
	
	private boolean getIfTokenIsExpired(Jwt jwt) {
		return Objects.requireNonNull(jwt.getExpiresAt()).isBefore(Instant.now());
	}
	
	public CustomUserDetails userDetails(String userId) {
		return userRepository.findById(userId)
				.map(CustomUserDetails::new)
				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
	}
}
