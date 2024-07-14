package com.fpoly.httc_sport.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.httc_sport.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
	List<Image> findAllByPublicIdIn(Iterable<String> publicIds);
	Optional<Image> findByPublicId(String publicId);
	void deleteByPublicId(String publicId);
	boolean existsByPublicId(String publicId);
}
