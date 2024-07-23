package com.fpoly.httc_sport.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.fpoly.httc_sport.dto.request.RentRequest;
import com.fpoly.httc_sport.dto.response.PayOSPaymentResponse;
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
	
	PaymentService paymentService;
	
	public RentInfoResponse rentPitch(RentRequest request) {
		var pitch = pitchRepository.findById(request.getPitchId()).orElseThrow(
				() -> new AppException(ErrorCode.PITCH_NOT_EXISTED)
		);
		
		var paymentMethod = paymentMethodRepository.findById(request.getPaymentMethod()).orElseThrow(
				() -> new AppException(ErrorCode.PAYMENT_METHOD_NOT_EXISTED)
		);
		
		LocalTime startTime = request.getStartTime().plusMinutes(1);
		LocalTime endTime = request.getStartTime().plusMinutes(request.getRentTime());
		LocalDate dateNow = LocalDate.now();
		LocalTime timeNow = LocalTime.now();
		LocalTime startStopTime = LocalTime.of(23, 0);
		LocalTime endStopTime = LocalTime.of(6, 0);
		
		if (rentInfoRepository.existsByPitchIdEqualsAndEmailEqualsAndRentedAtEqualsAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
				pitch.getId(), request.getEmail(),
				request.getRentedAt(), startTime, endTime))
			throw new AppException(ErrorCode.RENT_INFO_EXISTED);
		
		if (request.getRentedAt().getYear() < dateNow.getYear()) {
			return RentInfoResponse.builder().message("Đặt sân thất bại, năm đặt không hợp lệ").build();
		} else if (request.getRentedAt().getYear() == dateNow.getYear()) {
			if (request.getRentedAt().getDayOfYear() < dateNow.getDayOfYear())
				return RentInfoResponse.builder().message("Đặt sân thất bại, ngày đặt không hợp lệ").build();;
			
			if (request.getRentedAt().getDayOfYear() == dateNow.getDayOfYear())
				if (timeNow.isAfter(startTime))
					return RentInfoResponse.builder().message("Đặt sân thất bại, thời gian đặt không hợp lệ").build();
		}
		
		if (startTime.isAfter(startStopTime) || startTime.isBefore(endStopTime))
			return RentInfoResponse.builder().message("Đặt sân thất bại, sân bóng không hoạt động trong khoảng thời gian này").build();
		
		if (rentInfoRepository
				.existsByRentedAtEqualsAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
						request.getRentedAt(),
						startTime.plusSeconds(1),
						startTime.plusSeconds(1)) ||
			rentInfoRepository
				.existsByRentedAtEqualsAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
						request.getRentedAt(),
						endTime.minusSeconds(1),
						endTime.minusSeconds(1))
			) {
			return RentInfoResponse.builder().message("Đặt sân thất bại, ngày đặt hoặc thời gian đặt bị trùng").build();
		} else if (rentInfoRepository.existsByRentedAtEqualsAndEndTimeBetween(request.getRentedAt(), startTime, endTime))
			return RentInfoResponse.builder().message("Đặt sân thất bại, ngày đặt hoặc thời gian đặt bị trùng").build();
		
		var user = userRepository.findByEmail(request.getEmail()).orElse(null);
		
		var rentInfo = rentInfoMapper.toRentInfo(request);
		rentInfo.setPitch(pitch);
		rentInfo.setUser(user);
		rentInfo.setStartTime(startTime);
		rentInfo.setEndTime(endTime);
		rentInfo.setTotal((int) (((float) request.getRentTime() / 60) * (pitch.getPrice() * paymentMethod.getPriceRate())));
		rentInfo.setPaymentMethod(paymentMethod);
		
		rentInfo = rentInfoRepository.save(rentInfo);
		
		return RentInfoResponse.builder()
				.id(rentInfo.getId())
				.total(rentInfo.getTotal())
				.message("Đặt sân thành công")
				.build();
	}
	
	public RentInfoResponse confirmRent(String code, String id, String status) {
		if (code.equals("01") || !status.equals("PAID"))
			return RentInfoResponse.builder().message("Thanh toán đặt cọc thất bại").build();
		
		var paymentInfo = paymentService.getPaymentInfo(id);
		
		var rentInfo = rentInfoRepository.findById(paymentInfo.getData().getOrderCode()).orElseThrow(
				() -> new AppException(ErrorCode.RENT_INFO_NOT_EXISTED)
		);
		
		rentInfo.setDeposit(paymentInfo.getData().getAmountPaid());
		rentInfo.setPaymentStatus(true);
		
		rentInfoRepository.save(rentInfo);
		
		return RentInfoResponse.builder()
				.id(rentInfo.getId())
				.total(rentInfo.getTotal())
				.message("Thanh toán đặt cọc thành công")
				.build();
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
