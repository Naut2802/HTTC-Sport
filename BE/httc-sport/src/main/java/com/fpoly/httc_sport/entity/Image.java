package com.fpoly.httc_sport.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String publicId;
	String url;
	
	@ManyToOne
	@JoinColumn(name = "pitch_id")
	Pitch pitch;
}
