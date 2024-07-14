package com.fpoly.httc_sport.repository;

import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.httc_sport.entity.ActiveTime;

public interface ActiveTimeRepository extends JpaRepository<ActiveTime, Integer> {
	Optional<ActiveTime> findByStartTimeLessThanEqualAndEndTimeGreaterThanEqual(LocalTime time, LocalTime time2);
}
