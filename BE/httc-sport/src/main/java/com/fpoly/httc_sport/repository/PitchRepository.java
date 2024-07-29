package com.fpoly.httc_sport.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.httc_sport.entity.Pitch;

public interface PitchRepository extends JpaRepository<Pitch, Integer> {
	Page<Pitch> findAllByIsEnabledTrue(Pageable pageable);
	List<Pitch> findByPitchNameContainingAndIsEnabledTrue(String pitchName);
	List<Pitch> findByTypeEqualsAndIsEnabledTrue(String type);
}
