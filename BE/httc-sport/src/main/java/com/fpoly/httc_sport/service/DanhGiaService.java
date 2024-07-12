package com.fpoly.httc_sport.service;

import java.util.List;

import com.fpoly.httc_sport.repository.DanhGiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fpoly.httc_sport.entity.DanhGia;

@Service
public class DanhGiaService {
	@Autowired
	DanhGiaRepository danhGiaRepo;
	
	public List<DanhGia> findById(int maSan) {
		return  (List<DanhGia>) danhGiaRepo.findBySanMaSan(maSan);
	}
	public void save(DanhGia danhGia) {
		danhGiaRepo.save(danhGia);
	}
}
