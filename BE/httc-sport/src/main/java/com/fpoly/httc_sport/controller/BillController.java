package com.fpoly.httc_sport.controller;

import com.fpoly.httc_sport.dto.request.ReviewsRequest;
import com.fpoly.httc_sport.dto.response.ApiResponse;
import com.fpoly.httc_sport.dto.response.BillResponse;
import com.fpoly.httc_sport.service.BillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/bill")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Bill Controller")
public class BillController {
	BillService billService;
	
	@Operation(summary = "Get all bill with user-id")
	@GetMapping("/get-all-by-user/{userId}")
	ApiResponse<List<BillResponse>> getAllBillByUser(@PathVariable String userId,
	                              @RequestParam(defaultValue = "0") int page,
	                              @RequestParam(defaultValue = "5") int size) {
		return ApiResponse.<List<BillResponse>>builder()
				.result(billService.getAllBillByUserId(userId, page, size))
				.build();
	}
	
	@Operation(summary = "Get all bill for admin")
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<List<BillResponse>> getAllBill(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		return ApiResponse.<List<BillResponse>>builder()
				.result(billService.getAllBill(page, size))
				.build();
	}
	
	@Operation(summary = "Get all bill with pitch-id", description = "Api for admin")
	@GetMapping("/get-all-by-pitch/{pitchId}")
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<List<BillResponse>> getAllBillByPitch(@PathVariable int pitchId,
	                                 @RequestParam(defaultValue = "0") int page,
	                                 @RequestParam(defaultValue = "5") int size) {
		return ApiResponse.<List<BillResponse>>builder()
				.result(billService.getAllBillByPitchId(pitchId, page, size))
				.build();
	}
}
