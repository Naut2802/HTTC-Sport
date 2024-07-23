package com.fpoly.httc_sport.controller;

import com.fpoly.httc_sport.dto.response.ApiResponse;
import com.fpoly.httc_sport.service.PaymentService;
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
@Slf4j
public class PaymentController {
	PaymentService paymentService;
	
	@PostMapping("rent-pitch/{id}")
	ApiResponse<?> createRentPaymentLink(@PathVariable int id, @RequestParam("deposit") float deposit)
			throws NoSuchAlgorithmException, InvalidKeyException {
		return ApiResponse.builder()
				.result(paymentService.createRentPaymentLink(id, deposit))
				.build();
	}
}
