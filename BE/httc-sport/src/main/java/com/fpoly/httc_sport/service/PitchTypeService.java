package com.fpoly.httc_sport.service;

import java.util.List;

import com.fpoly.httc_sport.repository.PitchTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.httc_sport.entity.PitchType;

@Service
public class PitchTypeService {
	@Autowired
	private PitchTypeRepository pitchTypeRepository;
	
	public List<PitchType> getAllLoaiSan() {
		return pitchTypeRepository.findAll();
	}
}
