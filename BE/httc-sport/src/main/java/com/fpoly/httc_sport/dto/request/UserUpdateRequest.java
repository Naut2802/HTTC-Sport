package com.fpoly.httc_sport.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fpoly.httc_sport.exception.annotations.NotBlankAndSizeAndPattern;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
	@Pattern(regexp = "^[a-zA-Z0-9_]+@[a-z.]+\\.[a-z]{2,6}$", message = "EMAIL_INVALID")
	String email;
	@NotBlankAndSizeAndPattern(regexp = "^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝĂĐĨŨƠƯàáâãèéêìíòóôõùúýăđĩũơưẠ-ỹ\\s]+$", message = "FIRST_NAME")
	String firstName;
	@NotBlankAndSizeAndPattern(regexp = "^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝĂĐĨŨƠƯàáâãèéêìíòóôõùúýăđĩũơưẠ-ỹ\\s]+$", message = "LAST_NAME")
	String lastName;
	@Pattern(regexp = "^([0-9]{10,11})$", message = "PHONE_NUMBER_INVALID")
	String phoneNumber;
}
