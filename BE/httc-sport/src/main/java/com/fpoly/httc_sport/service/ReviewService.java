package com.fpoly.httc_sport.service;

import com.fpoly.httc_sport.dto.request.ReviewsRequest;
import com.fpoly.httc_sport.dto.response.PitchDetailsResponse;
import com.fpoly.httc_sport.dto.response.ReviewResponse;
import com.fpoly.httc_sport.entity.Bill;
import com.fpoly.httc_sport.entity.Review;
import com.fpoly.httc_sport.exception.AppException;
import com.fpoly.httc_sport.exception.ErrorCode;
import com.fpoly.httc_sport.mapper.ReviewMapper;
import com.fpoly.httc_sport.repository.BillRepository;
import com.fpoly.httc_sport.repository.PitchRepository;
import com.fpoly.httc_sport.repository.ReviewRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewService {
	ReviewRepository reviewRepository;
	BillRepository billRepository;
	PitchRepository pitchRepository;
	ReviewMapper reviewMapper;
	
	@Transactional
	public void reviewsPitch(long billId, ReviewsRequest request) {
		var bill = billRepository.findById(billId).orElseThrow(
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
				.bill(bill)
				.pitch(bill.getPitch())
				.user(bill.getUser())
				.build();
		
		bill.setIsRate(true);
		
		reviewRepository.save(review);
		billRepository.save(bill);
	}

	public ReviewResponse getReviewByBill(long billId) {
		var review = reviewRepository.findByBillId(billId).orElseThrow(
				() -> new AppException(ErrorCode.REVIEW_NOT_EXISTED)
		);
		var context = SecurityContextHolder.getContext();
		var user = review.getBill().getUser();
		
		if (!user.getUsername().equals(context.getAuthentication().getName()))
			throw new AppException(ErrorCode.UNAUTHENTICATED);
		
		return reviewMapper.toReviewResponse(review);
	}
	
	public List<ReviewResponse> getAllReviewByUser(String userId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		
		return reviewRepository.findByUserId(userId, pageable).stream().map(reviewMapper::toReviewResponse).toList();
	}
}
