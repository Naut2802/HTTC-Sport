package com.fpoly.httc_sport.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionResponse {
	int id;
	String walletId;
	int paymentAmount;
	String transactionType;
	boolean paymentStatus;
	LocalDateTime transactionDate;
	RentInfoResponse rentInfo;
	String message;
}
