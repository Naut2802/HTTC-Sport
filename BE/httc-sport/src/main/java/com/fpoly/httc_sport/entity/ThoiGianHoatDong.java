package com.fpoly.httc_sport.entity;

import java.time.LocalTime;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThoiGianHoatDong {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer ca;
	@Temporal(TemporalType.TIME)
	LocalTime thoiGianBatDau;
	@Temporal(TemporalType.TIME)
	LocalTime thoiGianKetThuc;
	Float hot;
	
//	@ManyToMany(mappedBy = "listThoiGianHoatDong")
//	List<San> listSan;
}
