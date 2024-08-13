package com.fpoly.httc_sport.controller;

import com.fpoly.httc_sport.dto.response.ApiResponse;
import com.fpoly.httc_sport.dto.response.BillResponse;
import com.fpoly.httc_sport.service.BillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/bill")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Bill Controller")
@Slf4j
public class BillController {
	BillService billService;
	
	@Operation(summary = "Get all bill with user-id")
	@GetMapping("/get-all-by-user/{userId}")
	ApiResponse<List<BillResponse>> getAllBillByUser(@PathVariable String userId,
	                              @RequestParam(defaultValue = "0") int page,
	                              @RequestParam(defaultValue = "5") int size) {
		log.info("[Bill Controller - Admin get all bills with user-id] Admin get all bills with user id: {}", userId);
		var response = billService.getAllBillByUserId(userId, page, size);
		log.info("[Bill Controller - Admin get all bill with user-id] total bills: {}", response.size());
		return ApiResponse.<List<BillResponse>>builder()
				.result(response)
				.build();
	}
	
	@Operation(summary = "Get all bill for admin")
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<List<BillResponse>> getAllBill(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		log.info("[Bill Controller - Admin get all bills] Admin get all bills");
		var response = billService.getAllBill(page, size);
		log.info("[Bill Controller - Admin get all bills] total bills: {}", response.size());
		return ApiResponse.<List<BillResponse>>builder()
				.result(response)
				.build();
	}
	
	@Operation(summary = "Get all bill with pitch-id", description = "Api for admin")
	@GetMapping("/get-all-by-pitch/{pitchId}")
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<List<BillResponse>> getAllBillByPitch(@PathVariable int pitchId,
	                                 @RequestParam(defaultValue = "0") int page,
	                                 @RequestParam(defaultValue = "5") int size) {
		log.info("[Bill Controller - Admin get all bills with pitch-id] Admin get all bills with pitch-id: {}", pitchId);
		var response = billService.getAllBillByPitchId(pitchId, page, size);
		log.info("[Bill Controller - Admin get all bills with pitch-id] total bills: {}", response.size());
		return ApiResponse.<List<BillResponse>>builder()
				.result(response)
				.build();
	}
}
