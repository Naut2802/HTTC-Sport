package com.fpoly.httc_sport.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

public final class Enum {
	@Getter
	@AllArgsConstructor
	public static enum RoleEnum {
		USER("USER"),
		ADMIN("ADMIN");
		private final String value;
	}
	@Getter
	@AllArgsConstructor
	public static enum BillStatusEnum {
		DONE("Đã nhận sân và hoàn tất thanh toán"),
		NO_ACCEPT("Không nhận sân, mất tiền cọc");
		private final String value;
		
	}
	public static enum PaymentMethodEnum {
		QR,
		WALLET
	}
}
