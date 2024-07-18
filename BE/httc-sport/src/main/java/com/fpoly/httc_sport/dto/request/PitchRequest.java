package com.fpoly.httc_sport.dto.request;

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
	@NotNull(message = "PITCH_NAME_NULL")
	String pitchName;
	@Size(min = 30000, message = "PITCH_MIN_PRICE")
	Double price;
	@NotNull(message = "PITCH_ADDRESS_NULL")
	String street;
	String ward;
	@NotNull(message = "PITCH_ADDRESS_NULL")
	String district;
	@NotNull(message = "PITCH_ADDRESS_NULL")
	String city;
	String description;
	@NotNull(message = "PITCH_TYPE_NULL")
	String type;
	@NotNull(message = "PITCH_TOTAL_NULL")
	int total;
	List<MultipartFile> images;
}
