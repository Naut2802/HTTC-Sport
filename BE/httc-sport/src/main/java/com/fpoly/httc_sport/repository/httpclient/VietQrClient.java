package com.fpoly.httc_sport.repository.httpclient;

import com.fpoly.httc_sport.dto.request.VietQrRequest;
import com.fpoly.httc_sport.dto.response.VietQrResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "viet-qr-client", url = "https://api.vietqr.io")
public interface VietQrClient {
	@PostMapping(value = "/v2/generate", produces = MediaType.APPLICATION_JSON_VALUE)
	VietQrResponse generateQrCode(@RequestBody VietQrRequest request, @RequestHeader("x-client-id") String clientId);
}
