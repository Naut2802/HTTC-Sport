package com.fpoly.httc_sport.dto.response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PitchResponse {
	String pitchName;
	Double price;
	String street;
	String ward;
	String district;
	String city;
	String description;
	Boolean isEnabled;
	String type;
	int total;
	int remaining;
	Set<ImageResponse> images;
}
