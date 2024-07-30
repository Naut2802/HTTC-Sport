package com.fpoly.httc_sport.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewsRequest {
	@NotNull(message = "REVIEW_RATE_NULL")
	@Min(value = 1, message = "REVIEW_RATE_MIN")
	@Max(value = 5, message = "REVIEW_RATE_MAX")
	int rate;
	String description;
}
