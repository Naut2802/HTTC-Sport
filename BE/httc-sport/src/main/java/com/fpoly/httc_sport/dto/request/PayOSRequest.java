package com.fpoly.httc_sport.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PayOSRequest {
	int orderCode;
	int amount;
	String description;
	String buyerName;
	String buyerEmail;
	String buyerPhone;
	String cancelUrl;
	String returnUrl;
	String signature;
}
