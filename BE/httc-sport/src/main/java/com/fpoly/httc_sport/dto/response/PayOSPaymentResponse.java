package com.fpoly.httc_sport.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PayOSPaymentResponse {
	String code;
	String desc;
	Data data;
	String signature;
	
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Data {
		String id;
		int orderCode;
		int amount;
		int amountPaid;
		int amountRemaining;
		String status;
		String createdAt;
		List<Transactions> transactions;
		String cancellationReason;
		String canceledAt;
		
		
		@Getter
		@Setter
		@NoArgsConstructor
		@AllArgsConstructor
		public static class Transactions {
			String reference;
			int amount;
			String accountNumber;
			String description;
			String transactionDateTime;
			String virtualAccountName;
			String virtualAccountNumber;
			String counterAccountBankId;
			String counterAccountBankName;
			String counterAccountName;
			String counterAccountNumber;
		}
	}
}
