package com.fpoly.httc_sport.entity;

import java.io.Serializable;
import java.util.Set;

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
public class Pitch implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	@Column(nullable = false)
	String pitchName;
	@Column(nullable = false)
	Double price;
	@Builder.Default
	Boolean isEnabled = false;
	String description;
	@Column(nullable = false)
	String type;
	@Column(nullable = false)
	int total;
	int remaining;

	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	Address address;
	
	@OneToMany(mappedBy = "pitch", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	Set<Image> images;
	
	@OneToMany(mappedBy = "pitch", fetch = FetchType.EAGER)
	Set<Comment> comments;

	@OneToMany(mappedBy = "pitch")
	Set<Bill> bills;

//	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinTable(name = "san_tghd", joinColumns = @JoinColumn(name = "maSan"), inverseJoinColumns = @JoinColumn(name = "ca"))
//	List<ThoiGianHoatDong> listThoiGianHoatDong;
}
