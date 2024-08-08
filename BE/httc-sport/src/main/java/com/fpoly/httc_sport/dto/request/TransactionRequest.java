package com.fpoly.httc_sport.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionRequest {
	@NotNull(message = "WALLET_ID_NULL")
	@NotBlank(message = "WALLET_ID_NULL")
	String walletId;
	@Min(value = 2000, message = "PAYMENT_AMOUNT_MIN")
	@Max(value = 500000, message = "PAYMENT_AMOUNT_MAX")
	int paymentAmount;
}
