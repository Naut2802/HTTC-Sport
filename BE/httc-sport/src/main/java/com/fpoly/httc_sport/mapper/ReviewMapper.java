package com.fpoly.httc_sport.mapper;

import com.fpoly.httc_sport.dto.response.ReviewResponse;
import com.fpoly.httc_sport.entity.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
	ReviewResponse toReviewResponse(Review review);
}