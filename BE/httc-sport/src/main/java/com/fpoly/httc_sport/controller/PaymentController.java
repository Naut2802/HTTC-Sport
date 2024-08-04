package com.fpoly.httc_sport.controller;

import com.fpoly.httc_sport.dto.response.ApiResponse;
import com.fpoly.httc_sport.dto.response.PayOSResponse;
import com.fpoly.httc_sport.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("api/v1/payment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Payment Controller")
public class PaymentController {
	PaymentService paymentService;
	
	@Operation(summary = "Create payment link", description = "Api use to create a payment link for rent pitch")
	@PostMapping("rent-pitch/{id}")
	ApiResponse<PayOSResponse> createRentPaymentLink(@PathVariable int id, @RequestParam("deposit") float deposit)
			throws NoSuchAlgorithmException, InvalidKeyException {
		return ApiResponse.<PayOSResponse>builder()
				.result(paymentService.createRentPaymentLink(id, deposit))
				.build();
	}
}
