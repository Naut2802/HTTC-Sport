package com.fpoly.httc_sport.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.httc_sport.entity.Pitch;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PitchRepository extends JpaRepository<Pitch, Integer>, JpaSpecificationExecutor<Pitch> {
	Page<Pitch> findAllByIsEnabledTrue(Pageable pageable);
}
