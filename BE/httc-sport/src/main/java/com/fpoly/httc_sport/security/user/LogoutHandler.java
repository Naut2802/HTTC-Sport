package com.fpoly.httc_sport.security.user;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
			log.info("[LogoutHandler:doLogout] :: Started ");
			
			log.info("[LogoutHandler:doLogout]Logout at Http Request: {}", request.getRequestURI());
			final String headers = request.getHeader(HttpHeaders.AUTHORIZATION);
			
			if (headers == null || !headers.startsWith("Bearer ")) {
				return;
			}
			
			final String accessToken = headers.substring(7);
			String jti = accessToken.substring(accessToken.length()-10);
			JwtDecoder jwtDecoder = NimbusJwtDecoder.withPublicKey(keyService.getPublicKey(keyService.getPublicKeyFromDb(jti))).build();
			final Jwt jwtToken = jwtDecoder.decode(accessToken);
			final String userId = jwtUtils.getUserId(jwtToken);
			
			if (!userId.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
				if (jwtUtils.isTokenValid(jwtToken)) {
					invalidatedTokenRepository.save(InvalidatedToken.builder()
							.token(jwtToken.getTokenValue())
							.expiryTime(Date.from(jwtToken.getIssuedAt()))
							.build());
				}
				
				Cookie cookie = new Cookie("refresh_token", null);
				cookie.setHttpOnly(true);
				cookie.setSecure(true);
				cookie.setMaxAge(0);
				response.addCookie(cookie);
				refreshTokenRepository.deleteById(jti);
			}
		} catch (AppException e) {
			try {
				log.error("[JwtAccessTokenFilter:doFilterInternal] AppException due to :{}", e.getErrorCode().getMessage());
				handleException(response, HttpStatus.GONE, e.getErrorCode().getMessage());
			} catch (IOException ex) {
				throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
			}
		} catch (Exception e) {
			try {
				log.error("[JwtAccessTokenFilter:doFilterInternal] Exception due to :{}", e.getMessage());
				if (e.getMessage().contains("Jwt expired")) {
					handleException(response, HttpStatus.GONE, e.getMessage());
				} else
					handleException(response, HttpStatus.NOT_ACCEPTABLE, e.getMessage());
			} catch (IOException ex) {
				throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
			}
		}
	}
	
	private void handleException(HttpServletResponse response, HttpStatus status, String message) throws IOException {
		response.setStatus(status.value());
		response.setContentType("application/json");
		Map<String, String> errorResponse = new HashMap<>();
		errorResponse.put("error", message);
		response.getOutputStream().write(new ObjectMapper().writeValueAsBytes(errorResponse));
	}
}
