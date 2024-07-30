package com.fpoly.httc_sport.entity;

import jakarta.persistence.*;
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
	
	@OneToOne
	@JoinColumn(name = "bill_id")
	Bill bill;
	
	@ManyToOne @JoinColumn(name = "pitch_id")
	Pitch pitch;
	
	@ManyToOne @JoinColumn(name = "user_id")
	User user;
}
