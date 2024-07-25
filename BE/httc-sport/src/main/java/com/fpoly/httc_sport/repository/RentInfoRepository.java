package com.fpoly.httc_sport.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.httc_sport.entity.RentInfo;

public interface RentInfoRepository extends JpaRepository<RentInfo, Integer> {
	List<RentInfo> findByPitchId(Integer pitchId);
	List<RentInfo> findByUserId(String userId);
	int countByRentedAtEqualsAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(LocalDate date, LocalTime time, LocalTime time2);
	int countByRentedAtEqualsAndStartTimeBetween(LocalDate date, LocalTime time, LocalTime time2);
	int countByRentedAtEqualsAndEndTimeBetween(LocalDate date, LocalTime time, LocalTime time2);
}
