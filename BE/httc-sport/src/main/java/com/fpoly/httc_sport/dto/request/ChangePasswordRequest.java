package com.fpoly.httc_sport.dto.request;

public record ChangePasswordRequest
		(String currentPassword,
		 String newPassword,
		 String confirmationPassword) { }
