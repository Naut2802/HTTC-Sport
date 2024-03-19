package com.fpoly.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "vip")
public class Vip {
	@Id
	private Integer bacVip;
	private Double mucBatDau;
	private Double mucKetThuc;
	private Float giamGia;
	public Vip(Integer bacVip) {
		super();
		this.bacVip = bacVip;
	}
	
	
}
