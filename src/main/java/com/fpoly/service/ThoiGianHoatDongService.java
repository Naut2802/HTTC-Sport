package com.fpoly.service;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fpoly.model.ThoiGianHoatDong;
import com.fpoly.repository.ThoiGianHoatDongRepo;

import jakarta.servlet.ServletContext;

@Service
public class ThoiGianHoatDongService {
	@Autowired
	ThoiGianHoatDongRepo thoiGianHoatDongRepo;
	@Autowired
	ServletContext application;
	
	public ThoiGianHoatDong getByTime(LocalTime time) {
		return thoiGianHoatDongRepo.findByThoiGianBatDauLessThanEqualAndThoiGianKetThucGreaterThanEqual(time, time).orElse(null);
	}
	
//	@Scheduled(fixedDelay = 1000, initialDelay = 1000)
//	public void checkTime() {
//		ThoiGianHoatDong tghd = getByTime(LocalTime.now());
//		application.setAttribute("tghd", tghd == null ? new ThoiGianHoatDong(4, null, null, 1f, null) : tghd);
//	}
	
	public List<ThoiGianHoatDong> getAll(){
		return thoiGianHoatDongRepo.findAll();
	}
}
