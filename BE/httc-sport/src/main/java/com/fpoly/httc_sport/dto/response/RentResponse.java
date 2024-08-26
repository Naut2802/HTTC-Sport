package com.fpoly.httc_sport.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RentResponse {
	int id;
	int total;
	@Builder.Default
	int deposit = 0;
	@Builder.Default
	boolean isRentSuccess = false;
	String message;
}
