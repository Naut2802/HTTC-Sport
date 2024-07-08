package com.fpoly.httc_sport.repository;

import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.httc_sport.entity.ThoiGianHoatDong;

public interface ThoiGianHoatDongRepository extends JpaRepository<ThoiGianHoatDong, Integer> {
	Optional<ThoiGianHoatDong> findByThoiGianBatDauLessThanEqualAndThoiGianKetThucGreaterThanEqual(LocalTime time, LocalTime time2);
}
