package com.fpoly.httc_sport.service;

import com.fpoly.httc_sport.dto.response.BillResponse;
import com.fpoly.httc_sport.entity.Bill;
import com.fpoly.httc_sport.mapper.BillMapper;
import com.fpoly.httc_sport.repository.BillRepository;
import com.fpoly.httc_sport.repository.UserRepository;
import com.fpoly.httc_sport.repository.VipRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BillService {
	UserRepository userRepository;
	VipRepository vipRepository;
	BillRepository billRepository;
	BillMapper billMapper;
	
	public void createBill(Bill bill) {
		var user = bill.getUser();
		var bills = billRepository.findByUserId(user.getId());
		var vips = vipRepository.findAll();
		
		int total = bills.stream().mapToInt(Bill::getTotal).sum() + bill.getTotal();
		
		vips.forEach(vip -> {
			if (total >= vip.getMin() && total <= vip.getMax()) {
				user.setVip(vip);
			}
		});
		
		userRepository.save(user);
		billRepository.save(bill);
	}
	
	public List<BillResponse> getAllBills(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return billRepository.findAll(pageable).stream().map(billMapper::toBillResponse).toList();
	}
	
	public List<BillResponse> getAllBillsByUserId(String userId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return billRepository.findByUserId(userId, pageable).stream().map(billMapper::toBillResponse).toList();
	}
	
	public List<BillResponse> getAllBillsByPitchId(long pitchId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return billRepository.findByPitchId(pitchId, pageable).stream().map(billMapper::toBillResponse).toList();
	}
	
	public List<BillResponse> getAllRatedBillsByUser(String userId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return billRepository.findByUserIdAndIsRateTrue(userId, pageable).stream().map(billMapper::toBillResponse).toList();
	}
	
	public List<BillResponse> getAllRatedBills(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return billRepository.findByIsRateTrue(pageable).stream().map(billMapper::toBillResponse).toList();
	}
}
