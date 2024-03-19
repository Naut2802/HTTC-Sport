package com.fpoly.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.fpoly.model.San;

public interface SanRepo extends CrudRepository<San, Integer> {
	List<San> findAllByTrangThaiSanTrue();
	List<San> findByTenSanContainingAndTrangThaiSanTrue(String tenSan);
	List<San> findByLoaiSanMaLoaiEqualsAndTrangThaiSanTrue(Integer maLoai);
}
