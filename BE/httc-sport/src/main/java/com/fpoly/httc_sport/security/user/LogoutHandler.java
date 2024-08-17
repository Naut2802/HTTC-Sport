package com.fpoly.httc_sport.security.user;

import com.fpoly.httc_sport.entity.InvalidatedToken;
import com.fpoly.httc_sport.exception.AppException;
import com.fpoly.httc_sport.exception.ErrorCode;
import com.fpoly.httc_sport.repository.InvalidatedTokenRepository;
import com.fpoly.httc_sport.repository.RefreshTokenRepository;
import com.fpoly.httc_sport.security.jwt.JwtUtils;
import com.fpoly.httc_sport.service.KeyService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LogoutHandler implements org.springframework.security.web.authentication.logout.LogoutHandler {
	InvalidatedTokenRepository invalidatedTokenRepository;
	RefreshTokenRepository refreshTokenRepository;
	KeyService keyService;
	JwtUtils jwtUtils;
	
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		try {
			log.info("[LogoutHandler:doLogout]Logout at Http Request: {}", request.getRequestURI());
			final String headers = request.getHeader(HttpHeaders.AUTHORIZATION);
			String jti = getRefreshTokenFromCookies(request);
			
			if (jti ==  null)
				throw new AppException(ErrorCode.UNAUTHENTICATED);
			
			Cookie cookie = new Cookie("rti", null);
			cookie.setHttpOnly(true);
			cookie.setSecure(true);
			cookie.setPath("/");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			
			if (headers == null || !headers.startsWith("Bearer ")) {
				refreshTokenRepository.deleteById(jti);
			} else {
				final String accessToken = headers.substring(7);
				JwtDecoder jwtDecoder = NimbusJwtDecoder.withPublicKey(keyService.getPublicKey(keyService.getPublicKeyFromDb(jti))).build();
				refreshTokenRepository.deleteById(jti);
				final Jwt jwtToken = jwtDecoder.decode(accessToken);
				final String userId = jwtUtils.getUserId(jwtToken);
				
				if (!userId.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
					if (jwtUtils.isTokenValid(jwtToken)) {
						invalidatedTokenRepository.save(InvalidatedToken.builder()
								.token(jwtToken.getTokenValue())
								.expiryTime(LocalDateTime.from(Objects.requireNonNull(jwtToken.getIssuedAt())))
								.build());
					}
				}
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			response.setStatus(HttpStatus.OK.value());
		}
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
}
