package com.fpoly.httc_sport.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.fpoly.httc_sport.dto.request.RentRequest;
import com.fpoly.httc_sport.dto.response.RentInfoResponse;
import com.fpoly.httc_sport.exception.AppException;
import com.fpoly.httc_sport.exception.ErrorCode;
import com.fpoly.httc_sport.mapper.RentInfoMapper;
import com.fpoly.httc_sport.repository.PaymentMethodRepository;
import com.fpoly.httc_sport.repository.PitchRepository;
import com.fpoly.httc_sport.repository.RentInfoRepository;
import com.fpoly.httc_sport.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.httc_sport.entity.RentInfo;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RentInfoService {
	RentInfoRepository rentInfoRepository;
	PitchRepository pitchRepository;
	UserRepository userRepository;
	PaymentMethodRepository paymentMethodRepository;
	RentInfoMapper rentInfoMapper;
	
	public RentInfoResponse rentPitch(RentRequest request) {
		var pitch = pitchRepository.findById(request.getPitchId()).orElseThrow(
				() -> new AppException(ErrorCode.PITCH_NOT_EXISTED)
		);
		
		var paymentMethod = paymentMethodRepository.findById(request.getPaymentMethod()).orElseThrow(
				() -> new AppException(ErrorCode.PAYMENT_METHOD_NOT_EXISTED)
		);
		
		if (rentInfoRepository.existsByPitchIdEqualsAndEmailEqualsAndRentedAtEqualsAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
				pitch.getId(), request.getEmail(),
				request.getRentDate(), request.getStartTime(),
				request.getStartTime().plusMinutes(request.getRentTime())))
			throw new AppException(ErrorCode.RENT_INFO_EXISTED);
		
		var user = userRepository.findByEmail(request.getEmail()).orElse(null);
		
		var rentInfo = rentInfoMapper.toRentInfo(request);
		rentInfo.setPitch(pitch);
		rentInfo.setUser(user);
		rentInfo.setEndTime(request.getStartTime().plusMinutes(request.getRentTime()));
		rentInfo.setTotal(pitch.getPrice() * paymentMethod.getPriceRate());
		
		return RentInfoResponse.builder().build();
	}
	
//	@Autowired
//	private RentInfoRepository ttRepository;
//
//	public void save(RentInfo ttsan) {
//		ttRepository.save(ttsan);
//	}
//
//	public List<RentInfo> findByMaLoai(){
//		return (List<RentInfo>) ttRepository.findAll();
//	}
//
//	public RentInfo getTTSan(long id) {
//		return ttRepository.findById(id).orElse(null);
//	}
//
//	public Boolean existsByDateAndTime(LocalDate date, LocalTime time) {
//		return ttRepository.existsByNgayDatEqualsAndThoiGianNhanSanLessThanEqualAndThoiGianKetThucGreaterThanEqual(date, time, time);
//	}
//
//	public Boolean existsByBetween(LocalDate date, LocalTime time1, LocalTime time2) {
//		return ttRepository.existsByNgayDatEqualsAndThoiGianNhanSanBetween(date, time1, time2) || ttRepository.existsByNgayDatEqualsAndThoiGianKetThucBetween(date, time1, time2);
//	}
}
