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
public class LoginRequest{
	@Size(min = 4, message = "USERNAME_INVALID")
	String username;
	@Size(min = 6, message = "PASSWORD_INVALID")
	String password;
}
