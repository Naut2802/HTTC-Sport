package com.fpoly.httc_sport.dto.request;

import com.fpoly.httc_sport.exception.annotations.NotBlankAndSizeAndPattern;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RentRequest {
	@NotNull(message = "PITCH_NULL")
	int pitchId;
	@NotBlank(message = "RENT_INFO_EMAIL_NULL")
	@NotNull(message = "RENT_INFO_EMAIL_NULL")
	@Pattern(regexp = "^[a-zA-Z0-9_]+@[a-z.]+\\.[a-z]{2,6}$", message = "EMAIL_INVALID")
	String email;
	@NotNull(message = "RENT_INFO_PHONE_NUMBER_NULL")
	@Pattern(regexp = "^([0-9]{10,11})$", message = "PHONE_NUMBER_INVALID")
	String phoneNumber;
	@NotNull(message = "RENT_INFO_FIRST_NAME_NULL")
	@NotBlankAndSizeAndPattern(regexp = "^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝĂĐĨŨƠƯàáâãèéêìíòóôõùúýăđĩũơưẠ-ỹ\\s]+$", message = "FIRST_NAME")
	String firstName;
	@NotBlankAndSizeAndPattern(regexp = "^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝĂĐĨŨƠƯàáâãèéêìíòóôõùúýăđĩũơưẠ-ỹ\\s]+$", message = "LAST_NAME")
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
	@NotBlank(message = "RENT_INFO_EMAIL_NULL")
	@NotNull(message = "RENT_INFO_PAYMENT_METHOD_NULL")
	String paymentMethod;
}
