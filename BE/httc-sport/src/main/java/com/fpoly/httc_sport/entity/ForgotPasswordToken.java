package com.fpoly.httc_sport.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ForgotPasswordToken {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String token;
	LocalDateTime expiryTime;
	
	@OneToOne
	User user;
}
