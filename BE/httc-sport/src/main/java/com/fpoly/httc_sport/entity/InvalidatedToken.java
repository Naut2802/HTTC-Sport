package com.fpoly.httc_sport.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvalidatedToken {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	String id;
	@Column(name = "token", nullable = false, columnDefinition = "TEXT")
	String token;
	Date expiryTime;
}
