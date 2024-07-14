package com.fpoly.httc_sport.entity;

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
public class RentInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Temporal(TemporalType.TIME)
	LocalTime startTime;
	@Temporal(TemporalType.TIME)
	LocalTime endTime;
	Boolean isDone;
	@Temporal(TemporalType.DATE)
	LocalDate rentedAt;
	Double total;
	Double deposit;
	String note;
	
	@ManyToOne
	@JoinColumn(name = "pitch_id")
	Pitch pitch;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	User user;
	String email;
	String phoneNumber;
	String firstName;
	String lastName;
}
