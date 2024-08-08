package com.fpoly.httc_sport.dto.request;

import com.fpoly.httc_sport.exception.annotations.NotBlankAndSizeAndPattern;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
	@NotBlank(message = "RENT_INFO_EMAIL_NULL")
	@NotNull(message = "RENT_INFO_EMAIL_NULL")
	@Pattern(regexp = ".+@.+\\.[a-z]+", message = "EMAIL_INVALID")
	String email;
	@NotNull(message = "RENT_INFO_PHONE_NUMBER_NULL")
	@Pattern(regexp = "^([0-9]{10,11})$", message = "PHONE_NUMBER_INVALID")
	String phoneNumber;
	@NotNull(message = "RENT_INFO_FIRST_NAME_NULL")
	@NotBlankAndSizeAndPattern(regexp = "^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝĂĐĨŨƠƯàáâãèéêìíòóôõùúýăđĩũơưẠ-ỹ\\s]+$", message = "RENT_INFO_FIRST_NAME")
	String firstName;
	@NotBlankAndSizeAndPattern(regexp = "^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝĂĐĨŨƠƯàáâãèéêìíòóôõùúýăđĩũơưẠ-ỹ\\s]+$", message = "RENT_INFO_LAST_NAME")
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
