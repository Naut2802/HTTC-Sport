package com.fpoly.httc_sport.entity;

import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
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
public class LoaiSan {
	@Id
	Integer maLoai;
	String tenLoai;
	
	@OneToMany(mappedBy = "loaiSan", fetch = FetchType.EAGER)
	Set<San> listSan;
}
