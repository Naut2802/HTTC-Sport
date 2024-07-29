package com.fpoly.httc_sport.controller;

import com.fpoly.httc_sport.dto.request.ReviewsRequest;
import com.fpoly.httc_sport.dto.response.ApiResponse;
import com.fpoly.httc_sport.service.BillService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/bill")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BillController {
	BillService billService;
	
	@PostMapping("{id}")
	ApiResponse<?> reviewsPitch(@PathVariable long id, ReviewsRequest request) {
		billService.reviewsPitch(id, request);
		
		return ApiResponse.builder()
				.message("Gửi đánh giá thành công")
				.build();
	}
}
