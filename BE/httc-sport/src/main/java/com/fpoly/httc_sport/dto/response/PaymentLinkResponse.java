package com.fpoly.httc_sport.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fpoly.httc_sport.entity.ChatRoom;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentLinkResponse {
	String checkoutUrl;
	boolean isSuccess;
}
