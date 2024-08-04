package com.fpoly.httc_sport.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PitchResponse {
	int id;
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
	ImageResponse image;
	double rate;
}
