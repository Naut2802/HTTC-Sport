package com.fpoly.httc_sport.config;

import com.fpoly.httc_sport.security.jwt.JwtFilter;
import com.fpoly.httc_sport.security.jwt.JwtUtils;
import com.fpoly.httc_sport.security.user.CustomUserDetailsService;
import com.fpoly.httc_sport.security.user.LogoutHandler;
import com.fpoly.httc_sport.service.KeyService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityConfig {
	JwtUtils jwtUtils;
	LogoutHandler logoutHandler;
	CustomUserDetailsService userDetailsService;
	KeyService keyService;
	
	@Order(1)
	@Bean
	public SecurityFilterChain signInSecurityFilterChain(HttpSecurity httpSecurity) throws Exception{
		return httpSecurity
				.securityMatcher(new AntPathRequestMatcher("/auth/sign-in/**"))
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
				.userDetailsService(userDetailsService)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(ex -> {
					ex.authenticationEntryPoint((request, response, authException) ->
							response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage()));
				})
				.httpBasic(AbstractHttpConfigurer::disable)
				.build();
	}
	
	@Order(2)
	@Bean
	public SecurityFilterChain apiFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.securityMatcher(new AntPathRequestMatcher("/api/v1/**"))
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(http -> http.anyRequest().authenticated())
				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(new JwtFilter(jwtUtils, keyService), UsernamePasswordAuthenticationFilter.class)
				.httpBasic(AbstractHttpConfigurer::disable);
		
		return httpSecurity.build();
	}
	
	@Order(3)
	@Bean
	public SecurityFilterChain logoutFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.securityMatcher(new AntPathRequestMatcher("/api/auth/logout/**"))
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(http -> http.anyRequest().authenticated())
				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.logout(logout -> logout
						.logoutUrl("/api/auth/logout")
						.addLogoutHandler(logoutHandler)
						.logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext())))
				.addFilterBefore(new JwtFilter(jwtUtils, keyService), UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling(ex -> ex.authenticationEntryPoint(new JwtAuthenticationEntryPoint()))
				.httpBasic(AbstractHttpConfigurer::disable);;
		
		return httpSecurity.build();
	}
	
	@Order(4)
	@Bean
	public SecurityFilterChain registerSecurityFilterChain(HttpSecurity httpSecurity) throws Exception{
		return httpSecurity
				.securityMatcher(new AntPathRequestMatcher("/auth/sign-up/**"))
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth ->
						auth.anyRequest().permitAll())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.httpBasic(AbstractHttpConfigurer::disable)
				.build();
	}
	
	@Order(5)
	@Bean
	public SecurityFilterChain forgotPasswordSecurityFilterChain(HttpSecurity httpSecurity) throws Exception{
		return httpSecurity
				.securityMatcher(new AntPathRequestMatcher("/user/forgot-password/**"))
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth ->
						auth.anyRequest().permitAll())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.httpBasic(AbstractHttpConfigurer::disable)
				.build();
	}
	
	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration configuration = new CorsConfiguration();
		
		configuration.addAllowedOrigin("http://localhost:3000");
		configuration.addAllowedMethod("*");
		configuration.addAllowedHeader("*");
		configuration.setAllowCredentials(true);
		
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);
		
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
}
