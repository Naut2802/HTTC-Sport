package com.fpoly.httc_sport.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRequest{
	@NotNull(message = "ROLE_NAME_NULL")
	@NotBlank(message = "ROLE_NAME_NULL")
	String roleName;
	String description;
}
