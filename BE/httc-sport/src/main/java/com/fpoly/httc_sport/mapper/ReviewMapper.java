package com.fpoly.httc_sport.mapper;

import com.fpoly.httc_sport.dto.response.ReviewResponse;
import com.fpoly.httc_sport.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
	@Mapping(target = "username", source = "user.username")
	@Mapping(target = "firstName", source = "user.firstName")
	ReviewResponse toReviewResponse(Review review);
}