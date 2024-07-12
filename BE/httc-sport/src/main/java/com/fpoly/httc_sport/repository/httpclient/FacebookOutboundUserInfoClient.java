package com.fpoly.httc_sport.repository.httpclient;

import com.fpoly.httc_sport.dto.response.FacebookUserResponse;
import com.fpoly.httc_sport.dto.response.GoogleUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "facebook-user-client", url = "https://graph.facebook.com")
public interface FacebookOutboundUserInfoClient {
	@GetMapping(value = "/me")
	FacebookUserResponse getUserInfo(@RequestParam("fields") String fields,
	                                 @RequestParam("access_token") String accessToken);
}
