package com.fpoly.httc_sport.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

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
public class RentInfo extends AbstractEntity {
	@Id
	Integer id;
	String email;
	String phoneNumber;
	String firstName;
	String lastName;
	LocalDate rentedAt;
	LocalTime startTime;
	LocalTime endTime;
	int total;
	@Builder.Default
	Integer deposit = 0;
	String note;
	int typePitch;
	@Builder.Default
	Boolean isDone = false;
	@Builder.Default
	Boolean paymentStatus = false;
	
	@ManyToOne
	@JoinColumn(name = "pitch_id")
	Pitch pitch;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	User user;
	
	@ManyToOne
	@JoinColumn(name = "payment_method")
	PaymentMethod paymentMethod;
	
	@OneToOne
	@JoinColumn(name = "transaction_id")
	Transaction transaction;
	
	@PrePersist
	void generateId() {
		String uuid = UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 6);
		this.id = Integer.parseInt(uuid);
	}
}
