package com.fpoly.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.model.DanhGia;

public interface DanhGiaRepo extends JpaRepository<DanhGia, Long> {
	List<DanhGia> findBySanMaSan(Integer maSan);
}
