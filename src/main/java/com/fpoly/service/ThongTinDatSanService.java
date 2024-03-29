package com.fpoly.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.model.San;
import com.fpoly.model.ThongTinDatSan;
import com.fpoly.repository.ThongTinDatRepository;

@Service
public class ThongTinDatSanService {
	@Autowired
	private ThongTinDatRepository ttRepository;
	
	public void save(ThongTinDatSan ttsan) {
		ttRepository.save(ttsan);
	}
	
	public List<ThongTinDatSan> findByMaLoai(){
		return (List<ThongTinDatSan>) ttRepository.findAll();
	}
	
	public ThongTinDatSan getTTSan(long id) {
		return ttRepository.findById(id).orElse(null);
	}
	
	public Boolean existsByDateAndTime(LocalDate date, LocalTime time) {
		return ttRepository.existsByNgayDatEqualsAndThoiGianNhanSanLessThanEqualAndThoiGianKetThucGreaterThanEqual(date, time, time);
	}
	
	public Boolean existsByBetween(LocalDate date, LocalTime time1, LocalTime time2) {
		return ttRepository.existsByNgayDatEqualsAndThoiGianNhanSanBetween(date, time1, time2) || ttRepository.existsByNgayDatEqualsAndThoiGianKetThucBetween(date, time1, time2);
	}
}
