package com.fpoly.httc_sport.dto.request;

import jakarta.validation.constraints.Email;
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
public class RegisterRequest{
	@NotNull(message = "USERNAME_NULL")
	@Size(min = 4, message = "USERNAME_INVALID")
	String username;
	@NotNull(message = "PASSWORD_NULL")
	@Size(min = 6, message = "PASSWORD_INVALID")
	String password;
	@NotNull(message = "EMAIL_NULL")
	@Email(message = "EMAIL_INVALID")
	String email;
}
