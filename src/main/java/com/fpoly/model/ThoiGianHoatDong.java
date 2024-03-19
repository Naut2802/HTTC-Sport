package com.fpoly.model;

import java.time.LocalTime;
import java.util.List;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "thoigian_hoatdong")
public class ThoiGianHoatDong {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ca;
	@Temporal(TemporalType.TIME)
	private LocalTime thoiGianBatDau;
	@Temporal(TemporalType.TIME)
	private LocalTime thoiGianKetThuc;
	private Float hot;
	
//	@ManyToMany(mappedBy = "listThoiGianHoatDong")
//	private List<San> listSan;
}
