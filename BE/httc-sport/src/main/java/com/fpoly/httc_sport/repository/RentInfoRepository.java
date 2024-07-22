package com.fpoly.httc_sport.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.httc_sport.entity.RentInfo;

public interface RentInfoRepository extends JpaRepository<RentInfo, Long> {
	List<RentInfo> findByPitchId(Integer pitchId);
	List<RentInfo> findByUserId(String userId);
	Boolean existsByPitchIdEqualsAndEmailEqualsAndRentedAtEqualsAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(Integer pitchId, String email, LocalDate date, LocalTime time, LocalTime time2);
	Boolean existsByRentedAtEqualsAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(LocalDate date, LocalTime time, LocalTime time2);
	Boolean existsByRentedAtEqualsAndStartTimeBetween(LocalDate date, LocalTime time, LocalTime time2);
	Boolean existsByRentedAtEqualsAndEndTimeBetween(LocalDate date, LocalTime time, LocalTime time2);
}
