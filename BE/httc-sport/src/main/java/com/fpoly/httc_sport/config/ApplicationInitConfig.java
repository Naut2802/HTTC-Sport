package com.fpoly.httc_sport.config;

import com.fpoly.httc_sport.entity.Role;
import com.fpoly.httc_sport.entity.User;
import com.fpoly.httc_sport.repository.RoleRepository;
import com.fpoly.httc_sport.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
	PasswordEncoder passwordEncoder;
	
	@NonFinal
	static final String ADMIN_USER_NAME = "admin";
	@NonFinal
	static final String ADMIN_PASSWORD = "admin";
	
	@Bean
	@ConditionalOnProperty(
			prefix = "spring",
			value = "datasource.driver-class-name",
			havingValue = "com.mysql.cj.jdbc.Driver")
	ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
		log.info("Initializing application............");
		return args -> {
			if (!userRepository.existsByUsername(ADMIN_USER_NAME)) {
				roleRepository.save(Role.builder()
						.roleName("USER")
						.description("User role")
						.build());
				
				Role adminRole = roleRepository.save(Role.builder()
						.roleName("ADMIN")
						.description("Admin role")
						.build());
				
				var roles = new HashSet<Role>();
				roles.add(adminRole);
				
				User user = User.builder()
						.username(ADMIN_USER_NAME)
						.password(passwordEncoder.encode(ADMIN_USER_NAME + ADMIN_PASSWORD))
						.roles(roles)
						.isEnabled(true)
						.build();
				
				userRepository.save(user);
				log.warn("Admin user has been created with default information: \"admin\". Please change it!");
			}
			log.info("Application initialization completed ...........");
		};
	}
}
