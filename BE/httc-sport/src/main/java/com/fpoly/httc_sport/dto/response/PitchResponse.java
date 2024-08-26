package com.fpoly.httc_sport.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PitchResponse {
	long id;
	String pitchName;
	int price;
	String street;
	String ward;
	String district;
	String city;
	String description;
	Boolean isEnabled;
	String type;
	int total;
	String image;
	double rate;
}
