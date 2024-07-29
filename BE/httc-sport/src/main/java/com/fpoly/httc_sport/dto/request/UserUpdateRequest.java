package com.fpoly.httc_sport.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserUpdateRequest{
	@NotNull(message = "EMAIL_NULL")
	@NotBlank(message = "EMAIL_NULL")
	@Email(message = "EMAIL_INVALID")
	String email;
	String firstName;
	String lastName;
	String phoneNumber;
}
