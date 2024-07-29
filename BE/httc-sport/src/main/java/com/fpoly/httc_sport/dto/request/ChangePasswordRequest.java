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
public class ChangePasswordRequest {
	@NotNull(message = "PASSWORD_NULL")
	@NotBlank(message = "PASSWORD_NULL")
	String currentPassword;
	@NotNull(message = "NEW_PASSWORD_NULL")
	@NotBlank(message = "NEW_PASSWORD_NULL")
	@Size(min = 5, message = "PASSWORD_INVALID")
	String newPassword;
	@NotNull(message = "CONFIRMATION_PASSWORD_NULL")
	@NotBlank(message = "CONFIRMATION_PASSWORD_NULL")
	String confirmationPassword;
}
