package com.fpoly.httc_sport.dto.request;

import com.fpoly.httc_sport.exception.annotations.NotBlankAndSizeAndPattern;
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
public class UserUpdateProfileRequest {
	@NotBlankAndSizeAndPattern(regexp = "^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝĂĐĨŨƠƯàáâãèéêìíòóôõùúýăđĩũơưẠ-ỹ\\s]+$", message = "FIRST_NAME")
	String firstName;
	@NotBlankAndSizeAndPattern(regexp = "^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝĂĐĨŨƠƯàáâãèéêìíòóôõùúýăđĩũơưẠ-ỹ\\s]+$", message = "LAST_NAME")
	String lastName;
	@Pattern(regexp = "^([0-9]{10,11})$", message = "PHONE_NUMBER_INVALID")
	String phoneNumber;
}
