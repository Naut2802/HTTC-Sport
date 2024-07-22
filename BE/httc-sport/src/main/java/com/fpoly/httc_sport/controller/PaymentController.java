package com.fpoly.httc_sport.controller;

import com.fpoly.httc_sport.dto.response.ApiResponse;
import com.fpoly.httc_sport.dto.response.PayOSResponse;
import com.fpoly.httc_sport.dto.response.VietQrResponse;
import com.fpoly.httc_sport.service.PaymentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("api/v1/payment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PaymentController {
	PaymentService paymentService;
	
	@PostMapping
	ApiResponse<VietQrResponse> generateQr() {
		return ApiResponse.<VietQrResponse>builder()
				.result(paymentService.generateQr())
				.build();
	}
	@PostMapping("pay-os")
	ApiResponse<PayOSResponse> generatePayOS() throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
		return ApiResponse.<PayOSResponse>builder()
				.result(paymentService.generatePayOS())
				.build();
	}
}
