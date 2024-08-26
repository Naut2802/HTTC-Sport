package com.fpoly.httc_sport.repository.httpclient;

import com.fpoly.httc_sport.dto.request.PayOSRequest;
import com.fpoly.httc_sport.dto.response.PayOSPaymentResponse;
import com.fpoly.httc_sport.dto.response.PayOSResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "pay-os-client", url = "https://api-merchant.payos.vn")
public interface PayOSClient {
	@PostMapping(value = "/v2/payment-requests", produces = MediaType.APPLICATION_JSON_VALUE)
	PayOSResponse generateQrCode(@RequestBody PayOSRequest request
			, @RequestHeader("x-client-id") String clientId
			, @RequestHeader("x-api-key") String apiKey);
	
	@GetMapping(value = "/v2/payment-requests/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	PayOSPaymentResponse getPaymentInfo(@PathVariable int id
			, @RequestHeader("x-client-id") String clientId
			, @RequestHeader("x-api-key") String apiKey);
}
