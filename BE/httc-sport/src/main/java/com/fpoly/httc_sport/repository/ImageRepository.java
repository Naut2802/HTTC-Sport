package com.fpoly.httc_sport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.httc_sport.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
	List<Image> findBySanMaSan(int maSan);
}
