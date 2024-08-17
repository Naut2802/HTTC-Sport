package com.fpoly.httc_sport.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshTokenWhiteList {
	@Id
	String id;
	@Column(name = "token", nullable = false, columnDefinition = "TEXT")
	String token;
	LocalDateTime expiryTime;
	@Column(name = "public_key", nullable = false, columnDefinition = "TEXT")
	String publicKey;
	@ManyToOne @JoinColumn(name = "user_id")
	User user;
}
