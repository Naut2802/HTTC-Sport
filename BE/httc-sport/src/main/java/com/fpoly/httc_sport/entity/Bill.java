package com.fpoly.httc_sport.entity;

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
public class Bill extends AbstractIdEntity {
	String email;
	String phoneNumber;
	String firstName;
	String lastName;
	LocalDate rentedAt;
	LocalTime startTime;
	LocalTime endTime;
	Integer total;
	Integer typePitch;
	@Builder.Default
	Boolean isRate = false;
	String billStatus;
	
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
