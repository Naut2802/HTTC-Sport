package com.fpoly.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.model.Image;

public interface ImageRepo extends JpaRepository<Image, Long> {
	List<Image> findBySanMaSan(int maSan);
}
