package com.fpoly.httc_sport.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.fpoly.httc_sport.dto.request.RentRequest;
import com.fpoly.httc_sport.dto.response.PayOSPaymentResponse;
import com.fpoly.httc_sport.dto.response.RentInfoResponse;
import com.fpoly.httc_sport.entity.MailInfo;
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
	MailerService mailerService;
	
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
		LocalTime endStopTime = LocalTime.of(6, 1);
		
		if (request.getRentedAt().getYear() < dateNow.getYear()) {
			return RentInfoResponse.builder().message("Đặt sân thất bại, năm đặt không hợp lệ").build();
		} else if (request.getRentedAt().getYear() == dateNow.getYear()) {
			if (request.getRentedAt().getDayOfYear() < dateNow.getDayOfYear())
				return RentInfoResponse.builder().message("Đặt sân thất bại, ngày đặt không hợp lệ").build();;
			
			if (request.getRentedAt().getDayOfYear() == dateNow.getDayOfYear())
				if (timeNow.isAfter(startTime))
					return RentInfoResponse.builder().message("Đặt sân thất bại, thời gian đặt không hợp lệ").build();
		}
		
		if (startTime.isAfter(startStopTime) || startTime.isBefore(endStopTime)
				|| endTime.isAfter(startStopTime) || endTime.isBefore(endStopTime))
			return RentInfoResponse.builder().message("Đặt sân thất bại, sân bóng không hoạt động trong khoảng thời gian này").build();
		
		int stepHour = 0;
		int time = request.getRentTime();
		
		while (time > 0) {
			stepHour = request.getRentTime() - time;
			if (startTime.plusMinutes(stepHour).isAfter(startStopTime) && startTime.plusMinutes(stepHour).isAfter(endStopTime))
				return RentInfoResponse.builder().message("Đặt sân thất bại, sân bóng không hoạt động trong khoảng thời gian này").build();
			time -= 60;
		}
		
		int countByStartTime = rentInfoRepository
				.countByRentedAtEqualsAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
						request.getRentedAt(),
						startTime.plusSeconds(1),
						startTime.plusSeconds(1));
		
		System.out.println("Count rent-info by start time: " + countByStartTime);
		
		int countByEndTime = rentInfoRepository
				.countByRentedAtEqualsAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
						request.getRentedAt(),
						endTime.minusSeconds(1),
						endTime.minusSeconds(1));
		
		System.out.println("Count rent-info by end time: " + countByStartTime);
		
		int countByStartTimeBetween = rentInfoRepository
				.countByRentedAtEqualsAndStartTimeBetween(request.getRentedAt(), startTime, endTime);
		
		System.out.println("Count rent-info by start time between: " + countByStartTimeBetween);
		
		int countByEndTimeBetween = rentInfoRepository
				.countByRentedAtEqualsAndEndTimeBetween(request.getRentedAt(), startTime, endTime);
		
		System.out.println("Count rent-info by end time between: " + countByEndTimeBetween);
		
		if ( countByStartTime >= pitch.getTotal() || countByEndTime >= pitch.getTotal() ) {
			return RentInfoResponse.builder().message("Đặt sân thất bại, ngày đặt hoặc thời gian đặt bị trùng").build();
		} else if ( countByStartTimeBetween >= pitch.getTotal() || countByEndTimeBetween >= pitch.getTotal() )
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
		
		MailInfo mailInfo = MailInfo.builder()
				.to(rentInfo.getEmail())
				.subject("Thông tin đặt sân")
				.body(mailerService.generateRentPitchBody(rentInfo))
				.build();
		
		mailerService.queue(mailInfo);
		
		return RentInfoResponse.builder()
				.id(rentInfo.getId())
				.total(rentInfo.getTotal())
				.deposit(rentInfo.getDeposit())
				.message("Thanh toán đặt cọc thành công, vui lòng kiểm tra thông tin đặt sân được gửi qua Email")
				.build();
	}
}
