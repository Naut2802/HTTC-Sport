package com.fpoly.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.model.LoaiSan;
import com.fpoly.repository.LoaiSanRepo;

@Service
public class LoaiSanService {
	@Autowired
	private LoaiSanRepo loaiSanRepository;
	
	public List<LoaiSan> getAllLoaiSan() {
		return loaiSanRepository.findAll();
	}
}
