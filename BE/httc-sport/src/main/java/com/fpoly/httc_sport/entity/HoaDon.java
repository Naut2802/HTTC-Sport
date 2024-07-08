package com.fpoly.httc_sport.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class HoaDon implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long maHd;
	@Temporal(TemporalType.DATE)
	LocalDate ngayXuat;
	Double tongTien;
	@Temporal(TemporalType.TIME)
	LocalTime thoiGianBatDau;
	@Temporal(TemporalType.TIME)
	LocalTime thoiGianKetThuc;
	Boolean isRate;
	
	@ManyToOne @JoinColumn(name = "maSan")
	San san;
	@ManyToOne @JoinColumn(name = "username")
	User user;
	
	String email;
	String phoneNumber;
}
