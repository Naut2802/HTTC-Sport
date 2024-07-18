package com.fpoly.httc_sport.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResetPasswordRequest{
	@Size(min = 5, message = "PASSWORD_INVALID")
	String newPassword;
	String confirmationPassword;
}
