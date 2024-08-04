package com.fpoly.httc_sport.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PitchDetailsResponse {
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
	int remaining;
	double rate;
	List<ImageResponse> images;
	List<ReviewResponse> reviews;
}
