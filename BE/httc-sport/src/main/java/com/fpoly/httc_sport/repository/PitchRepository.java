package com.fpoly.httc_sport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.httc_sport.entity.Pitch;

public interface PitchRepository extends JpaRepository<Pitch, Integer> {
	List<Pitch> findAllByIsEnabledTrue();
	List<Pitch> findByPitchNameContainingAndIsEnabledTrue(String pitchName);
	List<Pitch> findByTypeEqualsAndIsEnabledTrue(String type);
}
