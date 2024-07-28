package com.fpoly.httc_sport.dto.request;

import jakarta.validation.constraints.NotNull;
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
public class RentInfoUpdateRequest {
	@NotNull(message = "RENT_INFO_EMAIL_NULL")
	String email;
	@NotNull(message = "RENT_INFO_PHONE_NUMBER_NULL")
	String phoneNumber;
	@NotNull(message = "RENT_INFO_FIRST_NAME_NULL")
	String firstName;
	String lastName;
	@NotNull(message = "RENT_INFO_RENT_DATE_NULL")
	LocalDate rentedAt;
	@NotNull(message = "RENT_INFO_START_TIME_NULL")
	LocalTime startTime;
	@NotNull(message = "RENT_INFO_RENT_TIME_NULL")
	int rentTime;
	@NotNull(message = "RENT_INFO_TYPE_PITCH_NULL")
	int typePitch;
	String note;
}
