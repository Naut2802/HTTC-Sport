package com.fpoly.httc_sport.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "hinh_anh")
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String tenAnh;
	
	@ManyToOne
	@JoinColumn(name = "maSan")
	San san;

	public Image(String tenAnh, San san) {
		super();
		this.tenAnh = tenAnh;
		this.san = san;
	}
	
}
