package com.fpoly.httc_sport.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RentInfoResponse {
	int id;
	int pitchId;
	String pitchName;
	String email;
	String phoneNumber;
	String firstName;
	String lastName;
	LocalDate rentedAt;
	LocalTime startTime;
	LocalTime endTime;
	int total;
	@Builder.Default
	int deposit = 0;
	String note;
	int typePitch;
	Boolean isDone;
	Boolean paymentStatus;
}
