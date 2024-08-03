package com.fpoly.httc_sport.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.httc_sport.entity.RentInfo;

public interface RentInfoRepository extends JpaRepository<RentInfo, Integer> {
	Page<RentInfo> findByPitchId(Integer pitchId, Pageable pageable);
	Page<RentInfo> findByUserId(String userId, Pageable pageable);
	List<RentInfo> findByPitchIdAndRentedAtEqualsAndStartTimeLessThanEqualAndEndTimeGreaterThanEqualAndPaymentStatusTrue(Integer pitchId, LocalDate date, LocalTime time, LocalTime time2);
	List<RentInfo> findByPitchIdAndRentedAtEqualsAndStartTimeBetweenAndPaymentStatusTrue(Integer pitchId, LocalDate date, LocalTime time, LocalTime time2);
	List<RentInfo> findByPitchIdAndRentedAtEqualsAndEndTimeBetweenAndPaymentStatusTrue(Integer pitchId, LocalDate date, LocalTime time, LocalTime time2);
	List<RentInfo> findByRentedAtLessThanAndPaymentStatusFalse(LocalDate date);
}
