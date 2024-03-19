package com.fpoly.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "san")
public class San implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer maSan;
	private String tenSan;
	private Double giaSan;
	private String diaChi;
	private Boolean trangThaiSan;
	private String trangThaiHoatDong; 
	private String moTa;

	@ManyToOne
	@JoinColumn(name = "maLoai")
	private LoaiSan loaiSan;
	
	@OneToMany(mappedBy = "san", fetch = FetchType.EAGER)
	private List<Image> listHinhAnh;
	
	@OneToMany(mappedBy = "san", fetch = FetchType.EAGER)
	private List<DanhGia> listDanhGia;

	@OneToMany(mappedBy = "san", fetch = FetchType.EAGER)
	private List<HoaDon> listHoaDon;

//	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinTable(name = "san_tghd", joinColumns = @JoinColumn(name = "maSan"), inverseJoinColumns = @JoinColumn(name = "ca"))
//	private List<ThoiGianHoatDong> listThoiGianHoatDong;
}
