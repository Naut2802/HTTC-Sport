package com.fpoly.httc_sport.repository.httpclient;

import com.fpoly.httc_sport.dto.response.OutboundUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "oauth-user-client", url = "https://www.googleapis.com")
public interface OutboundUserInfoClient {
	@GetMapping(value = "/oauth2/v1/userinfo")
	OutboundUserResponse getUserInfo(@RequestParam("alt") String alt,
	                                 @RequestParam("access_token") String accessToken);
}
