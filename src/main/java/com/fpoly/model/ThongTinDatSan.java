package com.fpoly.model;

import java.sql.Time;
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
@Table(name = "thongtin_datsan")
public class ThongTinDatSan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Temporal(TemporalType.TIME)
	private LocalTime thoiGianNhanSan;
	@Temporal(TemporalType.TIME)
	private LocalTime thoiGianKetThuc;
	private Boolean trangThai;
	@Temporal(TemporalType.DATE)
	private LocalDate ngayDat;
	private Double tongTien;
	private Double tienCoc;
	private String ghiChu;
	
	@ManyToOne
	@JoinColumn(name = "maSan")
	private San san;
	
	@ManyToOne
	@JoinColumn(name = "username")
	private User user;
	private String email;
	private String phoneNumber;
	private String firstName;
	private String lastName;
}
