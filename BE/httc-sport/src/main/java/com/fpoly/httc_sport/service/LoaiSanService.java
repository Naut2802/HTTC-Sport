package com.fpoly.httc_sport.service;

import java.util.List;

import com.fpoly.httc_sport.repository.LoaiSanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.httc_sport.entity.LoaiSan;

@Service
public class LoaiSanService {
	@Autowired
	private LoaiSanRepository loaiSanRepository;
	
	public List<LoaiSan> getAllLoaiSan() {
		return loaiSanRepository.findAll();
	}
}
