package com.fpoly.httc_sport.dto.request;

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
	String firstName;
	String lastName;
	String phoneNumber;
}