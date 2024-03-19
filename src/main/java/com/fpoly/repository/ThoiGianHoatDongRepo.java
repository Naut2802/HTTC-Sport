package com.fpoly.repository;

import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.model.ThoiGianHoatDong;

public interface ThoiGianHoatDongRepo extends JpaRepository<ThoiGianHoatDong, Integer> {
	Optional<ThoiGianHoatDong> findByThoiGianBatDauLessThanEqualAndThoiGianKetThucGreaterThanEqual(LocalTime time, LocalTime time2);
}
