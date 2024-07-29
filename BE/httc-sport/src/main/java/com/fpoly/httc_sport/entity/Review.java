package com.fpoly.httc_sport.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	Integer rate;
	String description;
	
	@ManyToOne @JoinColumn(name = "pitch_id")
	Pitch pitch;
	
	@ManyToOne @JoinColumn(name = "user_id")
	User user;
}
