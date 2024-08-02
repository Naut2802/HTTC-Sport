package com.fpoly.httc_sport.service;

import com.fpoly.httc_sport.dto.request.ReviewsRequest;
import com.fpoly.httc_sport.dto.response.BillResponse;
import com.fpoly.httc_sport.entity.Bill;
import com.fpoly.httc_sport.mapper.BillMapper;
import com.fpoly.httc_sport.repository.BillRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BillService {
	BillRepository billRepository;
	BillMapper billMapper;
	
	public void createBill(Bill bill) {
		billRepository.save(bill);
	}
	
	public List<BillResponse> getAllBill(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return billRepository.findAll(pageable).stream().map(billMapper::toBillResponse).toList();
	}
	
	public List<BillResponse> getAllBillByUserId(String userId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return billRepository.findByUserId(userId, pageable).stream().map(billMapper::toBillResponse).toList();
	}
	
	public List<BillResponse> getAllBillByPitchId(int pitchId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return billRepository.findByPitchId(pitchId, pageable).stream().map(billMapper::toBillResponse).toList();
	}
}
