package com.fpoly.httc_sport.dto.response;

import com.fpoly.httc_sport.utils.Enum.VipEnum;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
	String id;
	String username;
	String email;
	String firstName;
	String lastName;
	String phoneNumber;
	Boolean isEnabled;
	Set<RoleResponse> roles;
	WalletResponse wallet;
	VipEnum vip;
}
