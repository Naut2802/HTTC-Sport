package com.fpoly.httc_sport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.fpoly.httc_sport.entity.San;

public interface SanRepository extends JpaRepository<San, Integer> {
	List<San> findAllByTrangThaiSanTrue();
	List<San> findByTenSanContainingAndTrangThaiSanTrue(String tenSan);
	List<San> findByLoaiSanMaLoaiEqualsAndTrangThaiSanTrue(Integer maLoai);
}
