package com.fpoly.httc_sport.service;

import java.time.LocalTime;
import java.util.List;

import com.fpoly.httc_sport.repository.ActiveTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.httc_sport.entity.ActiveTime;

import jakarta.servlet.ServletContext;

@Service
public class ActiveTimeService {
	@Autowired
	ActiveTimeRepository activeTimeRepository;
	@Autowired
	ServletContext application;
	
//	public ActiveTime getByTime(LocalTime time) {
//		return thoiGianHoatDongRepo.findByThoiGianBatDauLessThanEqualAndThoiGianKetThucGreaterThanEqual(time, time).orElse(null);
//	}
	
//	@Scheduled(fixedDelay = 1000, initialDelay = 1000)
//	public void checkTime() {
//		ThoiGianHoatDong tghd = getByTime(LocalTime.now());
//		application.setAttribute("tghd", tghd == null ? new ThoiGianHoatDong(4, null, null, 1f, null) : tghd);
//	}
	
//	public List<ActiveTime> getAll(){
//		return thoiGianHoatDongRepo.findAll();
//	}
}
