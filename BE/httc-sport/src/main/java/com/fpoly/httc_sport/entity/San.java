package com.fpoly.httc_sport.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class San implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer maSan;
	String tenSan;
	Double giaSan;
	String diaChi;
	Boolean trangThaiSan;
	String trangThaiHoatDong; 
	String moTa;

	@ManyToOne
	@JoinColumn(name = "maLoai")
	LoaiSan loaiSan;
	
	@OneToMany(mappedBy = "san", fetch = FetchType.EAGER)
	Set<Image> listHinhAnh;
	
	@OneToMany(mappedBy = "san", fetch = FetchType.EAGER)
	Set<DanhGia> listDanhGia;

	@OneToMany(mappedBy = "san", fetch = FetchType.EAGER)
	Set<HoaDon> listHoaDon;

//	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinTable(name = "san_tghd", joinColumns = @JoinColumn(name = "maSan"), inverseJoinColumns = @JoinColumn(name = "ca"))
//	List<ThoiGianHoatDong> listThoiGianHoatDong;
}
