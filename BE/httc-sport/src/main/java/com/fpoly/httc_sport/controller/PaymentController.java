package com.fpoly.httc_sport.controller;

import com.fpoly.httc_sport.dto.response.ApiResponse;
import com.fpoly.httc_sport.dto.response.PayOSResponse;
import com.fpoly.httc_sport.dto.response.PaymentLinkResponse;
import com.fpoly.httc_sport.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("api/v1/payment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Payment Controller")
@Slf4j
public class PaymentController {
	PaymentService paymentService;
	
	@Operation(summary = "Create rent pitch payment link", description = "Api use to create a payment link for rent pitch")
	@PostMapping("rent-pitch/{rentInfoId}")
	ApiResponse<PaymentLinkResponse> createRentPaymentLink(@PathVariable int rentInfoId, @RequestParam("deposit") float deposit)
			throws NoSuchAlgorithmException, InvalidKeyException {
		log.info("[Payment Controller - Create payment link api] Starting create payment link");
		var response = paymentService.createRentPaymentLink(rentInfoId, deposit);
		log.info("[Payment Controller - Create payment link api] Payment link created");
		return ApiResponse.<PaymentLinkResponse>builder()
				.result(response)
				.build();
	}
	
	@Operation(summary = "Create top up to wallet payment link", description = "User use this api for top up to wallet")
	@PostMapping("user-top-up/{transactionId}")
	ApiResponse<PaymentLinkResponse> createTopUpPaymentLink(@PathVariable int transactionId)
			throws NoSuchAlgorithmException, InvalidKeyException {
		log.info("[Payment Controller - Create top-up payment link api] Starting create top-up payment link");
		var response = paymentService.createTopUpPaymentLink(transactionId);
		log.info("[Payment Controller - Create top-up payment link api] Payment top-up link created");
		return ApiResponse.<PaymentLinkResponse>builder()
				.result(response)
				.build();
	}
}
