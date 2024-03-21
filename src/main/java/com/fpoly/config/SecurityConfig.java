package com.fpoly.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.fpoly.security.CustomUserDetailsService;
import com.fpoly.security.handler.CustomAuthenticationFailureHandler;
import com.fpoly.security.handler.CustomAuthenticationSuccessHandler;
import com.fpoly.security.handler.CustomLogoutSuccessHandler;
import com.fpoly.security.oauth2.CustomOAuth2UserService;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	CustomAuthenticationSuccessHandler successHandler;
	@Autowired
	CustomLogoutSuccessHandler logoutSuccessHander;
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	@Autowired
	CustomAuthenticationFailureHandler failureHandler;
	@Autowired
	CustomOAuth2UserService auth2UserService;
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	    provider.setUserDetailsService(customUserDetailsService);
	    provider.setPasswordEncoder(passwordEncoder());
	    return provider;
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(request ->
		        request.requestMatchers("/account/info", "/account/changePassword", "/account/updateProfile", "/user/**").authenticated()
			            .requestMatchers("/admin/**").hasAuthority("ADMIN")
				        .anyRequest().permitAll())
        .formLogin(login ->
		        login.loginPage("/account/login")
				        .loginProcessingUrl("/account/doLogin")
				        .successHandler(successHandler)
				        .failureHandler(failureHandler))
        .rememberMe(remember ->
		        remember.key("abc21412bf1u1ur2v2yf")
				        .tokenValiditySeconds(86400))
        .oauth2Login(login ->
		        login.loginPage("/account/login")
				        .authorizationEndpoint(e -> e.baseUri("/oauth2/authorization"))
				        .redirectionEndpoint(e -> e.baseUri("/login/oauth2/code/*"))
				        .userInfoEndpoint(e -> e.userService(auth2UserService))
				        .successHandler(successHandler)
				        .failureUrl("/account/loginFailure"))
        .logout(logout ->
		        logout.logoutUrl("/account/logout")
				        .logoutSuccessHandler(logoutSuccessHander))
        .exceptionHandling(e ->
		        e.accessDeniedHandler((request, response, accessDeniedException) -> response.sendRedirect("/home")))
		.authenticationProvider(authenticationProvider());
		return http.build();
	}
}
