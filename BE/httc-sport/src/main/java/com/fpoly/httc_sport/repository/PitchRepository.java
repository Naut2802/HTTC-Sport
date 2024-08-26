package com.fpoly.httc_sport.repository;

import com.fpoly.httc_sport.dto.response.PitchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.httc_sport.entity.Pitch;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PitchRepository extends JpaRepository<Pitch, Long>, JpaSpecificationExecutor<Pitch> {
	Page<Pitch> findAllByIsEnabledTrue(Pageable pageable);
	
	@Query("SELECT new com.fpoly.httc_sport.dto.response.PitchResponse(b.pitch.id, b.pitch.pitchName, b.pitch.price, " +
			"b.pitch.address.street, b.pitch.address.ward, b.pitch.address.district, b.pitch.address.city, " +
			"b.pitch.description, b.pitch.isEnabled, b.pitch.type, b.pitch.total, " +
			"(SELECT img.url FROM Image img WHERE img.pitch.id = b.pitch.id ORDER BY img.id ASC LIMIT 1), b.pitch.rate) " +
			"FROM Bill b GROUP BY b.pitch.id ORDER BY COUNT(b) DESC")
	List<PitchResponse> findTop3MostRentedPitch(Pageable pageable);
}
