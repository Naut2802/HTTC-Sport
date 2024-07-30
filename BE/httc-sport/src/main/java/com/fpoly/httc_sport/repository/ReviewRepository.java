package com.fpoly.httc_sport.repository;

import java.util.List;
import java.util.Optional;

import com.fpoly.httc_sport.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	Page<Review> findByUserId(String userId, Pageable pageable);
	Optional<Review> findByBillId(long billId);
}
