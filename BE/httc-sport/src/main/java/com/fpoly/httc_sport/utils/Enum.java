package com.fpoly.httc_sport.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

public final class Enum {
	
	public enum RoleEnum {
		USER,
		ADMIN
	}
	
	@Getter
	@AllArgsConstructor
	public enum BillStatusEnum {
		DONE("Đã nhận sân và hoàn tất thanh toán"),
		NO_ACCEPT("Không nhận sân, mất tiền cọc");
		private final String value;
		
	}
	
	public enum PaymentMethodEnum {
		QR,
		WALLET
	}
	
	@Getter
	@AllArgsConstructor
	public enum VipEnum {
		VIP_0(0, 999999, 1f),
		VIP_1(1000000, 2999999, 0.95f),
		VIP_2(3000000, 4999999, 0.90f),
		VIP_3(5000000, 1000000, 0.85f);
		
		private final int min;
		private final int max;
		private final float discountRate;
	}
	
	@Getter
	@AllArgsConstructor
	public enum TransactionTypeEnum {
		ADMIN_DEPOSIT("Admin nạp tiền vào ví"),
		DEPOSIT("Nạp tiền vào ví"),
		PAY("Thanh toán đặt sân");
		
		private final String value;
	}
	
}
