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
public class TransactionResponse {
	int id;
	String walletId;
	int paymentAmount;
	String transactionType;
	boolean paymentStatus;
	String message;
}
