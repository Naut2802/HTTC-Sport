package com.fpoly.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "loai_san")
public class LoaiSan {
	@Id
	private Integer maLoai;
	private String tenLoai;
	
	@OneToMany(mappedBy = "loaiSan", fetch = FetchType.EAGER)
	private List<San> listSan;
}
