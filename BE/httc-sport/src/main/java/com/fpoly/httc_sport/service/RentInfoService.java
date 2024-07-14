package com.fpoly.httc_sport.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fpoly.httc_sport.repository.RentInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.httc_sport.entity.RentInfo;

@Service
public class RentInfoService {
//	@Autowired
//	private RentInfoRepository ttRepository;
//
//	public void save(RentInfo ttsan) {
//		ttRepository.save(ttsan);
//	}
//
//	public List<RentInfo> findByMaLoai(){
//		return (List<RentInfo>) ttRepository.findAll();
//	}
//
//	public RentInfo getTTSan(long id) {
//		return ttRepository.findById(id).orElse(null);
//	}
//
//	public Boolean existsByDateAndTime(LocalDate date, LocalTime time) {
//		return ttRepository.existsByNgayDatEqualsAndThoiGianNhanSanLessThanEqualAndThoiGianKetThucGreaterThanEqual(date, time, time);
//	}
//
//	public Boolean existsByBetween(LocalDate date, LocalTime time1, LocalTime time2) {
//		return ttRepository.existsByNgayDatEqualsAndThoiGianNhanSanBetween(date, time1, time2) || ttRepository.existsByNgayDatEqualsAndThoiGianKetThucBetween(date, time1, time2);
//	}
}
