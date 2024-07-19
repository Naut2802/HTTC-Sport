package com.fpoly.httc_sport.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorizeUserRequest {
	@NotNull(message = "AUTHORIZE_ROLE_NULL")
	Set<String> roles;
}
