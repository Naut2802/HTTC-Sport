package com.fpoly.httc_sport.repository;

import java.util.List;

import com.fpoly.httc_sport.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	Page<Review> findByPitchId(int pitchId, Pageable pageable);
	Page<Review> findByUserId(String userId, Pageable pageable);
}
