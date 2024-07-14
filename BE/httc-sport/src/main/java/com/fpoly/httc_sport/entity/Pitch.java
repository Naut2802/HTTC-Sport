package com.fpoly.httc_sport.entity;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	String pitchName;
	Double price;
	String address;
	Boolean isEnabled;
	String activeStatus;
	String description;

	@ManyToOne
	@JoinColumn(name = "type_id")
	PitchType pitchType;
	
	@OneToMany(mappedBy = "pitch", fetch = FetchType.EAGER)
	Set<Image> listImage;
	
	@OneToMany(mappedBy = "pitch", fetch = FetchType.EAGER)
	Set<Comment> listComment;

	@OneToMany(mappedBy = "pitch", fetch = FetchType.EAGER)
	Set<Bill> listBill;

//	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinTable(name = "san_tghd", joinColumns = @JoinColumn(name = "maSan"), inverseJoinColumns = @JoinColumn(name = "ca"))
//	List<ThoiGianHoatDong> listThoiGianHoatDong;
}
