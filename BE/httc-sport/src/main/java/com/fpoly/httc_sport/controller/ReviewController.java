package com.fpoly.httc_sport.controller;

import com.fpoly.httc_sport.dto.request.ReviewsRequest;
import com.fpoly.httc_sport.dto.response.ApiResponse;
import com.fpoly.httc_sport.dto.response.PitchDetailsResponse;
import com.fpoly.httc_sport.dto.response.ReviewResponse;
import com.fpoly.httc_sport.service.BillService;
import com.fpoly.httc_sport.service.ReviewService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/review")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewController {
	ReviewService reviewService;
	
	@PostMapping("{billId}")
	ApiResponse<?> reviewsPitch(@PathVariable long billId, @Valid @RequestBody ReviewsRequest request) {
		reviewService.reviewsPitch(billId, request);
		
		return ApiResponse.builder()
				.message("Gửi đánh giá thành công")
				.build();
	}
	
	@GetMapping("{billId}")
	ApiResponse<ReviewResponse> getReviewByBill(@PathVariable long billId) {
		return ApiResponse.<ReviewResponse>builder()
				.result(reviewService.getReviewByBill(billId))
				.build();
	}
	
	@GetMapping("/get-all-by-user/{userId}")
	ApiResponse<List<ReviewResponse>> getAllReviewByUser(@PathVariable String userId,
	                                                     @RequestParam(defaultValue = "0") int page,
	                                                     @RequestParam(defaultValue = "5") int size) {
		return ApiResponse.<List<ReviewResponse>>builder()
				.result(reviewService.getAllReviewByUser(userId, page, size))
				.build();
	}
}
