package com.fpoly.httc_sport.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class Vip {
	@Id
	Integer bacVip;
	Double mucBatDau;
	Double mucKetThuc;
	Float giamGia;
	public Vip(Integer bacVip) {
		super();
		this.bacVip = bacVip;
	}
	
	
}
