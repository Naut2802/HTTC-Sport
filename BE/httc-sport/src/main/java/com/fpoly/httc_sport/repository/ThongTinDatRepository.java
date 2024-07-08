package com.fpoly.httc_sport.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.httc_sport.entity.ThongTinDatSan;

public interface ThongTinDatRepository extends JpaRepository<ThongTinDatSan, Long> {
	List<ThongTinDatSan> findBySanMaSan(Integer maLoai);

	Boolean existsByNgayDatEqualsAndThoiGianNhanSanLessThanEqualAndThoiGianKetThucGreaterThanEqual(LocalDate date, LocalTime time, LocalTime time2);
	Boolean existsByNgayDatEqualsAndThoiGianNhanSanBetween(LocalDate date, LocalTime time, LocalTime time2);
	Boolean existsByNgayDatEqualsAndThoiGianKetThucBetween(LocalDate date, LocalTime time, LocalTime time2);
	List<ThongTinDatSan> findByUserUsername(String username);
}
