package com.fpoly.httc_sport.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PayOSResponse {
	String code;
	String desc;
	Data data;
	String signature;
	
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Data {
		String bin;
		String accountNumber;
		String accountName;
		int amount;
		String description;
		int orderCode;
		String currency;
		String paymentLinkId;
		String status;
		String checkoutUrl;
		String qrCode;
	}
}
