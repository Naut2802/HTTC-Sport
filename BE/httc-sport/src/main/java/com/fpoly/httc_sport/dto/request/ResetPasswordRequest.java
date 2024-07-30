package com.fpoly.httc_sport.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
	@NotBlank(message = "NEW_PASSWORD_NULL")
	@NotNull(message = "NEW_PASSWORD_NULL")
	@Size(min = 5, message = "PASSWORD_INVALID")
	String newPassword;
	@NotBlank(message = "CONFIRMATION_PASSWORD_NULL")
	@NotNull(message = "CONFIRMATION_PASSWORD_NULL")
	String confirmationPassword;
}
