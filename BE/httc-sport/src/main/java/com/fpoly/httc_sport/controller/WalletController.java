package com.fpoly.httc_sport.controller;

import com.fpoly.httc_sport.dto.request.TransactionRequest;
import com.fpoly.httc_sport.dto.response.ApiResponse;
import com.fpoly.httc_sport.dto.response.TransactionResponse;
import com.fpoly.httc_sport.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/v1/wallet")
@Tag(name = "Wallet Controller")
@Slf4j
public class WalletController {
	WalletService walletService;
	
	@Operation(summary = "Api top up to wallet", description = "User use this api to top up to wallet")
	@PostMapping("top-up")
	ApiResponse<TransactionResponse> topUp(@Valid @RequestBody TransactionRequest request) {
		var response = walletService.createTopUpTransaction(request);
		
		return ApiResponse.<TransactionResponse>builder()
				.message(response.getMessage())
				.result(response)
				.build();
	}
	
	@Operation(summary = "Api confirm transaction", description = "User use this api to confirm top up transaction")
	@PostMapping("confirm-transaction")
	ApiResponse<TransactionResponse> confirmTransaction(@RequestParam("code") String code,
	                                                @RequestParam("orderCode") int orderCode,
	                                                @RequestParam("status") String status) {
		var response = walletService.confirmTopUpTransaction(code, orderCode, status);
		
		return ApiResponse.<TransactionResponse>builder()
				.message(response.getMessage())
				.result(response)
				.build();
	}
	
	@Operation(summary = "Api admin top up to wallet for user", description = "Admin use this api to top up to wallet for user")
	@PostMapping("admin/top-up-user/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<TransactionResponse> adminTopUpUser(@PathVariable String userId, @Valid @RequestBody TransactionRequest request) {
		log.info("[Wallet Controller - Admin top up for user api] Admin top-up a user with user-id: {}", userId);
		var response = walletService.adminTopUpUser(userId, request);
		log.info("[Wallet Controller - Admin top up for user api] {}", response.getMessage());
		
		return ApiResponse.<TransactionResponse>builder()
				.message(response.getMessage())
				.result(response)
				.build();
	}
}
