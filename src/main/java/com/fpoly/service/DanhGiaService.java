package com.fpoly.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.model.DanhGia;
import com.fpoly.repository.DanhGiaRepo;
@Service
public class DanhGiaService {
	@Autowired
	DanhGiaRepo danhGiaRepo;
	
	public List<DanhGia> findById(int maSan) {
		return  (List<DanhGia>) danhGiaRepo.findBySanMaSan(maSan);
	}
	public void save(DanhGia danhGia) {
		danhGiaRepo.save(danhGia);
	}
}
