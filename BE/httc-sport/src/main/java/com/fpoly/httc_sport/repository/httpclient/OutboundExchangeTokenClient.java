package com.fpoly.httc_sport.repository.httpclient;

import com.fpoly.httc_sport.dto.request.ExchangeTokenRequest;
import com.fpoly.httc_sport.dto.response.ExchangeTokenResponse;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "oauth-client", url = "https://oauth2.googleapis.com")
public interface OutboundExchangeTokenClient {
	@PostMapping(value = "/token", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	ExchangeTokenResponse exchangeToken(@QueryMap ExchangeTokenRequest request);
}
