package com.fpoly.httc_sport.service;

import com.fpoly.httc_sport.dto.request.ReviewsRequest;
import com.fpoly.httc_sport.entity.Bill;
import com.fpoly.httc_sport.entity.Review;
import com.fpoly.httc_sport.exception.AppException;
import com.fpoly.httc_sport.exception.ErrorCode;
import com.fpoly.httc_sport.repository.BillRepository;
import com.fpoly.httc_sport.repository.ReviewRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BillService {
	BillRepository billRepository;
	ReviewRepository reviewRepository;
	
	public void createBill(Bill bill) {
		billRepository.save(bill);
	}
	
	public void reviewsPitch(long id, ReviewsRequest request) {
		var bill = billRepository.findById(id).orElseThrow(
				() -> new AppException(ErrorCode.BILL_NOT_EXISTED)
		);
		var user = bill.getUser();
		
		if (user == null)
			throw new AppException(ErrorCode.UNAUTHENTICATED);
		
		var context = SecurityContextHolder.getContext();
		
		if (!user.getUsername().equals(context.getAuthentication().getName()))
			throw new AppException(ErrorCode.UNAUTHENTICATED);
		
		if (bill.getIsRate())
			throw new AppException(ErrorCode.REVIEW_EXISTED);
		
		var review = Review.builder()
				.rate(request.getRate())
				.description(request.getDescription())
				.pitch(bill.getPitch())
				.user(bill.getUser())
				.build();
		
		bill.setIsRate(true);
		
		billRepository.save(bill);
		reviewRepository.save(review);
	}
}
