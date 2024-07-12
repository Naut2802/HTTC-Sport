package com.fpoly.httc_sport.repository.httpclient;

import com.fpoly.httc_sport.dto.request.GoogleExchangeTokenRequest;
import com.fpoly.httc_sport.dto.response.GoogleExchangeTokenResponse;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "google-client", url = "https://oauth2.googleapis.com")
public interface GoogleOutboundExchangeTokenClient {
	@PostMapping(value = "/token", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	GoogleExchangeTokenResponse exchangeToken(@QueryMap GoogleExchangeTokenRequest request);
}
