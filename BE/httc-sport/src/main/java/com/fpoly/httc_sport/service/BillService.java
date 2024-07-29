package com.fpoly.httc_sport.service;

import com.fpoly.httc_sport.entity.Bill;
import com.fpoly.httc_sport.exception.AppException;
import com.fpoly.httc_sport.exception.ErrorCode;
import com.fpoly.httc_sport.repository.BillRepository;
import com.fpoly.httc_sport.repository.CommentRepository;
import com.fpoly.httc_sport.repository.PitchRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BillService {
	BillRepository billRepository;
	CommentRepository commentRepository;
	PitchRepository pitchRepository;
	
	public void createBill(Bill bill) {
		if (billRepository.existsById(bill.getId()))
			throw new AppException(ErrorCode.EXISTED);
		
		billRepository.save(bill);
	}
	
//	public BillResponse reviewsPitch() {
//
//	}
}
