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
public class LoginRequest{
	@NotBlank(message = "USERNAME_NULL")
	@NotNull(message = "USERNAME_NULL")
	@Size(min = 4, message = "USERNAME_INVALID")
	String username;
	@NotBlank(message = "PASSWORD_NULL")
	@NotNull(message = "PASSWORD_NULL")
	@Size(min = 5, message = "PASSWORD_INVALID")
	String password;
}
