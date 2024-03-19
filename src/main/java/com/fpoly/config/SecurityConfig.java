package com.fpoly.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
		http.csrf().disable()
        .authorizeRequests()
        	.requestMatchers("/account/info", "/account/changePassword", "/account/updateProfile", "/user/**").authenticated()
        	.requestMatchers("/admin/**").hasAuthority("ADMIN")
	        .anyRequest().permitAll()
        .and()
        .formLogin().loginPage("/account/login").loginProcessingUrl("/account/doLogin").failureHandler(failureHandler).successHandler(successHandler)
        .and()
        .rememberMe()
	        .key("abc21412bf1u1ur2v2yf") // Key để tạo và xác minh cookie Remember Me
	        .tokenValiditySeconds(86400)
	    .and()
        .oauth2Login()
        	.loginPage("/account/login")
            .authorizationEndpoint(e -> e.baseUri("/oauth2/authorization"))
            .redirectionEndpoint(e -> e.baseUri("/login/oauth2/code/*"))
            .userInfoEndpoint().userService(auth2UserService)
            .and()
            .failureHandler(failureHandler)
            .successHandler(successHandler)
        .and()
        .logout().logoutUrl("/account/logout").logoutSuccessHandler(logoutSuccessHander)
        .and()
        .exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) -> response.sendRedirect("/home"))
        .and()
		.authenticationProvider(authenticationProvider());
		return http.build();
	}
}
