package com.fpoly.httc_sport.service;

import com.fpoly.httc_sport.dto.response.AnalyticsResponse;
import com.fpoly.httc_sport.dto.response.ReportResponse;
import com.fpoly.httc_sport.entity.Bill;
import com.fpoly.httc_sport.mapper.BillMapper;
import com.fpoly.httc_sport.repository.BillRepository;
import com.fpoly.httc_sport.repository.PitchRepository;
import com.fpoly.httc_sport.repository.UserRepository;
import com.fpoly.httc_sport.utils.Enum.RoleEnum;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ReportService {
	UserRepository userRepository;
	PitchRepository pitchRepository;
	BillRepository billRepository;
	BillMapper billMapper;
	
	public ReportResponse getReportByDate(LocalDate fromDate, LocalDate toDate, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		var bills = billRepository.findByRentedAtBetween(fromDate, toDate, pageable);
		
		var total = bills.stream().map(Bill::getTotal).reduce(0, Integer::sum);
		
		var billReponses = bills.map(billMapper::toBillResponse).toList();
		
		return ReportResponse.builder()
				.bills(billReponses)
				.total(total)
				.build();
	}
	
	public AnalyticsResponse analytics() {
		int totalUser = userRepository.countAllByRolesRoleNameNot(RoleEnum.ADMIN);
		log.info("API Analytics. Total users = {}", totalUser);
		int totalPitches = (int) pitchRepository.count();
		log.info("API Analytics. Total pitches = {}", totalPitches);
		
		return AnalyticsResponse.builder()
				.totalUser(totalUser)
				.totalPitches(totalPitches)
				.build();
	}
}