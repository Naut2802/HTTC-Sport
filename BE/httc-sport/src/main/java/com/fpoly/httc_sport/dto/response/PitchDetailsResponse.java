package com.fpoly.httc_sport.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PitchDetailsResponse {
	String pitchName;
	Double price;
	String street;
	String ward;
	String district;
	String city;
	String description;
	Boolean isEnabled;
	String activeStatus;
	int typeId;
	Set<ImageResponse> images;
	Set<CommentResponse> comments;
}