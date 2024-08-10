package com.fpoly.httc_sport.dto.request;

import com.fpoly.httc_sport.exception.annotations.NotBlankAndSizeAndPattern;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest{
	@NotNull(message = "USERNAME_NULL")
	@NotBlankAndSizeAndPattern(min = 4, regexp = "^[a-zA-Z0-9]{4,20}$", message = "USERNAME")
	String username;
	@NotNull(message = "PASSWORD_NULL")
	@NotBlankAndSizeAndPattern(min = 5, regexp = "^[a-zA-Z0-9]{5,20}$", message = "PASSWORD")
	String password;
	@NotNull(message = "EMAIL_NULL")
	@NotBlank(message = "EMAIL_NULL")
	@Pattern(regexp = "^[a-zA-Z0-9_]+@[a-z.]+\\.[a-z]{2,6}$", message = "EMAIL_INVALID")
	String email;
}
