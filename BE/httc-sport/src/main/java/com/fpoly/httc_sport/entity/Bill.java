package com.fpoly.httc_sport.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Bill implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Temporal(TemporalType.DATE)
	LocalDate createdAt;
	Double total;
	@Temporal(TemporalType.TIME)
	LocalTime startTime;
	@Temporal(TemporalType.TIME)
	LocalTime endTime;
	Boolean isRate;
	
	@ManyToOne @JoinColumn(name = "pitch_id")
	Pitch pitch;
	@ManyToOne @JoinColumn(name = "user_id")
	User user;
	
	String email;
	String phoneNumber;
}