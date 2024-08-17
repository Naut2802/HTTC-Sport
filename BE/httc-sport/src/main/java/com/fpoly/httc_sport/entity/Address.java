package com.fpoly.httc_sport.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Address extends AbstractIdEntity {
	String street;
	String ward;
	String district;
	String city;
	
	@OneToOne
	@JoinColumn(name = "pitch_id")
	Pitch pitch;
}
