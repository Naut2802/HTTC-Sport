package com.fpoly.httc_sport.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

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
public class Bill implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String email;
	String phoneNumber;
	String firstName;
	String lastName;
	@Temporal(TemporalType.DATE)
	LocalDate createdAt;
	@Temporal(TemporalType.DATE)
	LocalDate rentedAt;
	@Temporal(TemporalType.TIME)
	LocalTime startTime;
	@Temporal(TemporalType.TIME)
	LocalTime endTime;
	Integer total;
	Integer typePitch;
	@Builder.Default
	Boolean isRate = false;
	
	@OneToOne(mappedBy = "bill", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	Review review;
	
	@ManyToOne
	@JoinColumn(name = "pitch_id")
	Pitch pitch;
	@ManyToOne
	@JoinColumn(name = "user_id")
	User user;
	
	@ManyToOne
	@JoinColumn(name = "payment_method")
	PaymentMethod paymentMethod;
}
