package com.fpoly.httc_sport.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpoly.httc_sport.exception.AppException;
import com.fpoly.httc_sport.security.user.CustomUserDetails;
import com.fpoly.httc_sport.service.KeyService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
	JwtUtils jwtUtils;
	KeyService keyService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                                HttpServletResponse response,
	                                FilterChain filterChain) throws ServletException, IOException {
		try {
			log.info("[JwtAccessTokenFilter:doFilterInternal] :: Started ");
			
			log.info("[JwtAccessTokenFilter:doFilterInternal]Filtering the Http Request:{}", request.getRequestURI());
			
			final String headers = request.getHeader(HttpHeaders.AUTHORIZATION);
			
			
			if (headers == null || !headers.startsWith("Bearer ")) {
				filterChain.doFilter(request, response);
				return;
			}
			
			final String token = headers.substring(7);
			JwtDecoder jwtDecoder =
					NimbusJwtDecoder.withPublicKey(
							keyService.getPublicKey(keyService.getPublicKeyFromDb(
									token.substring(token.length()-10)
							))
					).build();
			
			final Jwt jwtToken = jwtDecoder.decode(token);
			
			final String userId = jwtUtils.getUserId(jwtToken);
			
			System.out.println(getClientIp(request));
			
			if (!userId.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
				CustomUserDetails userDetails = jwtUtils.userDetails(userId);
				
				if (jwtUtils.isTokenValid(jwtToken)) {
					SecurityContext context = SecurityContextHolder.createEmptyContext();
					
					UsernamePasswordAuthenticationToken createdToken =
							new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					createdToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					context.setAuthentication(createdToken);
					SecurityContextHolder.setContext(context);
				}
			}
			log.info("[JwtAccessTokenFilter:doFilterInternal] Completed");
			
			filterChain.doFilter(request, response);
		} catch (AppException e) {
			log.error("[JwtAccessTokenFilter:doFilterInternal] AppException due to :{}", e.getErrorCode().getMessage());
			handleException(response, HttpStatus.GONE, e.getErrorCode().getMessage());
		} catch (Exception e) {
			log.error("[JwtAccessTokenFilter:doFilterInternal] Exception due to :{}", e.getMessage());
			
			if (e.getMessage().contains("Jwt expired")) {
				handleException(response, HttpStatus.GONE, e.getMessage());
			} else
				handleException(response, HttpStatus.NOT_ACCEPTABLE, e.getMessage());
		}
	}
	
	private void handleException(HttpServletResponse response, HttpStatus status, String message) throws IOException {
		response.setStatus(status.value());
		response.setContentType("application/json");
		Map<String, String> errorResponse = new HashMap<>();
		errorResponse.put("error", message);
		response.getOutputStream().write(new ObjectMapper().writeValueAsBytes(errorResponse));
	}
	
	public static String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
			// X-Forwarded-For may return multiple IPs, the first one is the client IP
			return ip.split(",")[0];
		}
		ip = request.getHeader("Proxy-Client-IP");
		if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("WL-Proxy-Client-IP");
		if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("HTTP_CLIENT_IP");
		if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}
}
