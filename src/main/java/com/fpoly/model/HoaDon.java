package com.fpoly.model;

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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "hoa_don")
public class HoaDon implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long maHd;
	@Temporal(TemporalType.DATE)
	private LocalDate ngayXuat;
	private Double tongTien;
	@Temporal(TemporalType.TIME)
	private LocalTime thoiGianBatDau;
	@Temporal(TemporalType.TIME)
	private LocalTime thoiGianKetThuc;
	private Boolean isRate;
	
	@ManyToOne @JoinColumn(name = "maSan")
	private San san;
	@ManyToOne @JoinColumn(name = "username")
	private User user;
	
	private String email;
	private String phoneNumber;
}
