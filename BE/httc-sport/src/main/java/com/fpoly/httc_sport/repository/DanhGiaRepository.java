package com.fpoly.httc_sport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.httc_sport.entity.DanhGia;

public interface DanhGiaRepository extends JpaRepository<DanhGia, Long> {
	List<DanhGia> findBySanMaSan(Integer maSan);
}
