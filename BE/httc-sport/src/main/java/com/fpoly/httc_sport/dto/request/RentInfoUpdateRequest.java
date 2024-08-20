package com.fpoly.httc_sport.dto.request;

import com.fpoly.httc_sport.exception.annotations.NotBlankAndSizeAndPattern;
import com.fpoly.httc_sport.utils.Constant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RentInfoUpdateRequest {
	@NotNull(message = "RENT_INFO_RENT_TIME_NULL")
	int rentTime;
}
