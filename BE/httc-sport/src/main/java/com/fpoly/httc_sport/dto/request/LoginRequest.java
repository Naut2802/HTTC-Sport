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
public class LoginRequest{
	@NotNull(message = "USERNAME_NULL")
	@NotBlankAndSizeAndPattern(min = 4, message = "USERNAME")
	String username;
	@NotNull(message = "PASSWORD_NULL")
	@NotBlankAndSizeAndPattern(min = 5, regexp = "^[a-zA-Z0-9]{5,20}$", message = "PASSWORD")
	String password;
}
