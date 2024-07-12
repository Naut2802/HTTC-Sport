package com.fpoly.httc_sport.repository.httpclient;

import com.fpoly.httc_sport.dto.request.FacebookExchangeTokenRequest;
import com.fpoly.httc_sport.dto.response.FacebookExchangeTokenResponse;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "facebook-client", url = "https://graph.facebook.com/v20.0/oauth")
public interface FacebookOutboundExchangeTokenClient {
	@GetMapping(value = "/access_token")
	FacebookExchangeTokenResponse exchangeToken(@QueryMap FacebookExchangeTokenRequest request);
}
