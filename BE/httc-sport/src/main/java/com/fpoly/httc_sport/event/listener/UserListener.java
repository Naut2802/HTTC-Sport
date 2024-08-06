package com.fpoly.httc_sport.event.listener;

import com.fpoly.httc_sport.entity.User;
import com.fpoly.httc_sport.entity.Wallet;
import jakarta.persistence.PrePersist;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class UserListener {
	@PrePersist
	public void onPersist(User user) {
		log.info("[User Listener] Creating a new user: {}", user.getUsername());
		
		user.setWallet(Wallet.builder()
						.user(user)
						.money(0)
				.build());
		
		log.info("[User Listener] Created wallet for: {}", user.getUsername());
	}
}
