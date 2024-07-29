package com.fpoly.httc_sport.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PitchRequest {
	@NotBlank(message = "PITCH_NAME_NULL")
	@NotNull(message = "PITCH_NAME_NULL")
	String pitchName;
	@NotBlank(message = "PITCH_PRICE_NULL")
	@NotNull(message = "PITCH_PRICE_NULL")
//	@DecimalMin(value = "300000", message = "PITCH_MIN_PRICE")
	Double price;
	@NotBlank(message = "PITCH_ADDRESS_NULL")
	@NotNull(message = "PITCH_ADDRESS_NULL")
	String street;
	String ward;
	@NotBlank(message = "PITCH_ADDRESS_NULL")
	@NotNull(message = "PITCH_ADDRESS_NULL")
	String district;
	@NotBlank(message = "PITCH_ADDRESS_NULL")
	@NotNull(message = "PITCH_ADDRESS_NULL")
	String city;
	String description;
	@NotBlank(message = "PITCH_TYPE_NULL")
	@NotNull(message = "PITCH_TYPE_NULL")
	String type;
	@NotBlank(message = "PITCH_TOTAL_NULL")
	@NotNull(message = "PITCH_TOTAL_NULL")
	int total;
	List<MultipartFile> images;
}
