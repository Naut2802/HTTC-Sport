package com.fpoly.httc_sport.dto.request;

import com.fpoly.httc_sport.exception.annotations.NotBlankAndSizeAndPattern;
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
	@NotNull(message = "NEW_PASSWORD_NULL")
	@NotBlankAndSizeAndPattern(min = 5, regexp = "^[a-zA-Z0-9]{5,20}$", message = "PASSWORD")
	String newPassword;
	@NotBlank(message = "CONFIRMATION_PASSWORD_NULL")
	@NotNull(message = "CONFIRMATION_PASSWORD_NULL")
	String confirmationPassword;
}
