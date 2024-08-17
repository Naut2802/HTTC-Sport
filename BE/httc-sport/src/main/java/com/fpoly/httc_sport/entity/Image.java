package com.fpoly.httc_sport.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Image extends AbstractIdEntity {
	String publicId;
	String url;
	
	@ManyToOne
	@JoinColumn(name = "pitch_id")
	Pitch pitch;
}
