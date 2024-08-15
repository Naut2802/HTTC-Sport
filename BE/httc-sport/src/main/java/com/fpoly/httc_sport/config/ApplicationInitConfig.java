package com.fpoly.httc_sport.config;

import com.fpoly.httc_sport.entity.*;
import com.fpoly.httc_sport.repository.PaymentMethodRepository;
import com.fpoly.httc_sport.repository.RoleRepository;
import com.fpoly.httc_sport.repository.UserRepository;
import com.fpoly.httc_sport.repository.VipRepository;
import com.fpoly.httc_sport.utils.Enum.*;
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

import java.util.*;

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
	@NonFinal
	static final Map<PaymentMethodEnum, Float> paymentMethodMap = new HashMap<>();
	
	@Bean
	@ConditionalOnProperty(
			prefix = "spring",
			value = "datasource.driver-class-name",
			havingValue = "com.mysql.cj.jdbc.Driver")
	ApplicationRunner applicationRunner(UserRepository userRepository,
	                                    RoleRepository roleRepository,
										VipRepository vipRepository,
	                                    PaymentMethodRepository paymentMethodRepository) {
		log.info("Initializing application............");
		return args -> {
			if (!userRepository.existsByUsername(ADMIN_USER_NAME)) {
				roleRepository.save(Role.builder()
						.roleName(RoleEnum.USER)
						.description("User role")
						.build());
				
				Role adminRole = roleRepository.save(Role.builder()
						.roleName(RoleEnum.ADMIN)
						.description("Admin role")
						.build());
				
				var roles = List.of(adminRole);
				
				User user = User.builder()
						.username(ADMIN_USER_NAME)
						.password(passwordEncoder.encode(ADMIN_USER_NAME + ADMIN_PASSWORD))
						.email("maousama333@gmail.com")
						.roles(roles)
						.wallet(new Wallet())
						.isEnabled(true)
						.build();
				
				userRepository.save(user);
				log.warn("Admin user has been created with default information: \"admin\". Please change it!");
			}
			
			if (vipRepository.findAll().isEmpty()) {
				int totalLevel = 4;
				
				for (int level = 0; level < totalLevel; level++) {
					VipEnum vip = VipEnum.valueOf("VIP_" + level);
					
					vipRepository.save(Vip.builder()
							.level(vip)
							.min(vip.getMin())
							.max(vip.getMax())
							.discountRate(vip.getDiscountRate())
							.build());
				}
			}
			
			paymentMethodMap.put(PaymentMethodEnum.QR, 1.05f);
			paymentMethodMap.put(PaymentMethodEnum.WALLET, 1f);
			
			paymentMethodMap.forEach((key, value) -> {
				if (!paymentMethodRepository.existsByMethod(key))
					paymentMethodRepository.save(PaymentMethod.builder()
							.method(key)
							.priceRate(value)
							.build());
			});
			
			log.info("Application initialization completed ...........");
		};
	}
}
