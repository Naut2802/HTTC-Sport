package com.fpoly.httc_sport.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateProfileRequest {
	String firstName;
	String lastName;
	@Pattern(regexp = "^([0-9]{10,11})$", message = "PHONE_NUMBER_INVALID")
	String phoneNumber;
}
