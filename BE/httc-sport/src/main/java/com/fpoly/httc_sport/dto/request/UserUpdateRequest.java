package com.fpoly.httc_sport.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fpoly.httc_sport.exception.annotations.NotBlankAndSizeAndPattern;
import com.fpoly.httc_sport.utils.Constant;
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
	@Pattern(regexp = Constant.EMAIL_REGEX, message = "EMAIL_INVALID")
	String email;
	@NotBlankAndSizeAndPattern(regexp = Constant.NAME_REGEX, message = "FIRST_NAME")
	String firstName;
	@NotBlankAndSizeAndPattern(regexp = Constant.NAME_REGEX, message = "LAST_NAME")
	String lastName;
	@Pattern(regexp = Constant.PHONE_NUMBER_REGEX, message = "PHONE_NUMBER_INVALID")
	String phoneNumber;
}
