package com.fpoly.httc_sport.entity;

import java.util.List;

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
public class Pitch extends AbstractIdEntity {
	@Column(nullable = false)
	String pitchName;
	@Column(nullable = false)
	int price;
	@Builder.Default
	Boolean isEnabled = true;
	String description;
	@Column(nullable = false)
	String type;
	@Column(nullable = false)
	int total;
	@Builder.Default
	double rate = 0;
	
	@OneToOne(mappedBy = "pitch", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	Address address;
	
	@OneToMany(mappedBy = "pitch", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	List<Image> images;
	
	@OneToMany(mappedBy = "pitch", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	List<Review> reviews;

	@OneToMany(mappedBy = "pitch")
	List<Bill> bills;
}
