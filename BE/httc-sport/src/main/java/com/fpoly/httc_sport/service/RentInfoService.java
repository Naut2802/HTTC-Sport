package com.fpoly.httc_sport.service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.fpoly.httc_sport.dto.request.RentInfoUpdateRequest;
import com.fpoly.httc_sport.dto.request.RentRequest;
import com.fpoly.httc_sport.dto.response.RentInfoResponse;
import com.fpoly.httc_sport.dto.response.RentResponse;
import com.fpoly.httc_sport.entity.Bill;
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
import org.springframework.stereotype.Service;

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
	BillService billService;
	
	public RentResponse rentPitch(RentRequest request) {
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
			return RentResponse.builder().message("Đặt sân thất bại, năm đặt không hợp lệ").build();
		} else if (request.getRentedAt().getYear() == dateNow.getYear()) {
			if (request.getRentedAt().getDayOfYear() < dateNow.getDayOfYear())
				return RentResponse.builder().message("Đặt sân thất bại, ngày đặt không hợp lệ").build();;
			
			if (request.getRentedAt().getDayOfYear() == dateNow.getDayOfYear())
				if (timeNow.isAfter(startTime))
					return RentResponse.builder().message("Đặt sân thất bại, thời gian đặt không hợp lệ").build();
		}
		
		if (startTime.isAfter(startStopTime) || startTime.isBefore(endStopTime)
				|| endTime.isAfter(startStopTime) || endTime.isBefore(endStopTime))
			return RentResponse.builder().message("Đặt sân thất bại, sân bóng không hoạt động trong khoảng thời gian này").build();
		
		int stepHour = 0;
		int time = request.getRentTime();
		
		while (time > 0) {
			stepHour = request.getRentTime() - time;
			if (startTime.plusMinutes(stepHour).isAfter(startStopTime) && startTime.plusMinutes(stepHour).isAfter(endStopTime))
				return RentResponse.builder().message("Đặt sân thất bại, sân bóng không hoạt động trong khoảng thời gian này").build();
			time -= 60;
		}
		
		int initCount = request.getTypePitch() == 5 ? 1
				: request.getTypePitch() == 7 ? 3
				: request.getTypePitch() == 11 ? 9 : 1;
		
		AtomicInteger countByStartTime = new AtomicInteger(initCount);
		rentInfoRepository
				.findByPitchIdAndRentedAtEqualsAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
						pitch.getId(),
						request.getRentedAt(),
						startTime.plusSeconds(1),
						startTime.plusSeconds(1))
				.forEach(rentInfo -> {
					if (rentInfo.getTypePitch() == 5)
						countByStartTime.addAndGet(1);
					else if (rentInfo.getTypePitch() == 7)
						countByStartTime.addAndGet(3);
				});
		System.out.println("Count rent-info by start time: " + countByStartTime);
		
		AtomicInteger countByEndTime = new AtomicInteger(initCount);
		rentInfoRepository
				.findByPitchIdAndRentedAtEqualsAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
						pitch.getId(),
						request.getRentedAt(),
						endTime.minusSeconds(1),
						endTime.minusSeconds(1))
				.forEach(rentInfo -> {
					if (rentInfo.getTypePitch() == 5)
						countByEndTime.addAndGet(1);
					else if (rentInfo.getTypePitch() == 7)
						countByEndTime.addAndGet(3);
				});
		System.out.println("Count rent-info by end time: " + countByStartTime);
		
		AtomicInteger countByStartTimeBetween = new AtomicInteger(initCount);
		rentInfoRepository
				.findByPitchIdAndRentedAtEqualsAndStartTimeBetween(pitch.getId(), request.getRentedAt(), startTime, endTime)
				.forEach(rentInfo -> {
					if (rentInfo.getTypePitch() == 5)
						countByStartTimeBetween.addAndGet(1);
					else if (rentInfo.getTypePitch() == 7)
						countByStartTimeBetween.addAndGet(3);
				});
		System.out.println("Count rent-info by start time between: " + countByStartTimeBetween);
		
		AtomicInteger countByEndTimeBetween = new AtomicInteger(initCount);
		rentInfoRepository
				.findByPitchIdAndRentedAtEqualsAndEndTimeBetween(pitch.getId(), request.getRentedAt(), startTime, endTime)
				.forEach(rentInfo -> {
					if (rentInfo.getTypePitch() == 5)
						countByEndTimeBetween.addAndGet(1);
					else if (rentInfo.getTypePitch() == 7)
						countByEndTimeBetween.addAndGet(3);
				});
		System.out.println("Count rent-info by end time between: " + countByEndTimeBetween);
		
		if ( countByStartTime.get() > pitch.getTotal() || countByEndTime.get() > pitch.getTotal() ) {
			return RentResponse.builder().message("Đặt sân thất bại, không còn sân trống trong khoảng thời gian này").build();
		} else if ( countByStartTimeBetween.get() > pitch.getTotal() || countByEndTimeBetween.get() > pitch.getTotal() )
			return RentResponse.builder().message("Đặt sân thất bại, không còn sân trống trong khoảng thời gian này").build();
		
		var user = userRepository.findByEmail(request.getEmail()).orElse(null);
		
		var rentInfo = rentInfoMapper.toRentInfo(request);
		rentInfo.setPitch(pitch);
		rentInfo.setUser(user);
		rentInfo.setStartTime(startTime);
		rentInfo.setEndTime(endTime);
		int total = (int) (((float) request.getRentTime() / 60) * (pitch.getPrice() * paymentMethod.getPriceRate()));
		rentInfo.setTotal(rentInfo.getTypePitch() == 5 ? total
				: rentInfo.getTypePitch() == 7 ? total * 3
				: rentInfo.getTypePitch() == 11 ? total * 9 : total);
		rentInfo.setPaymentMethod(paymentMethod);
		
		rentInfo = rentInfoRepository.save(rentInfo);
		
		return RentResponse.builder()
				.id(rentInfo.getId())
				.total(rentInfo.getTotal())
				.message("Đặt sân thành công")
				.build();
	}
	
	public RentResponse confirmRent(String code, String id, String status) {
		var paymentInfo = paymentService.getPaymentInfo(id);
		
		var rentInfo = rentInfoRepository.findById(paymentInfo.getData().getOrderCode()).orElseThrow(
				() -> new AppException(ErrorCode.RENT_INFO_NOT_EXISTED)
		);
		
		if (code.equals("01") || !status.equals("PAID")) {
			rentInfoRepository.delete(rentInfo);
			return RentResponse.builder().message("Thanh toán đặt cọc thất bại").build();
		}
		
		rentInfo.setDeposit(paymentInfo.getData().getAmountPaid());
		rentInfo.setPaymentStatus(true);
		
		rentInfoRepository.save(rentInfo);
		
		MailInfo mailInfo = MailInfo.builder()
				.to(rentInfo.getEmail())
				.subject("Thông tin đặt sân")
				.body(mailerService.generateRentPitchBody(rentInfo))
				.build();
		
		mailerService.queue(mailInfo);
		
		return RentResponse.builder()
				.id(rentInfo.getId())
				.total(rentInfo.getTotal())
				.deposit(rentInfo.getDeposit())
				.message("Thanh toán đặt cọc thành công, vui lòng kiểm tra thông tin đặt sân được gửi qua Email")
				.build();
	}

	public List<RentInfoResponse> getRentInfoResponses() {
		return rentInfoRepository.findAll().stream().map(rentInfoMapper::toRentInfoResponse).toList();
	}
	
	public RentInfoResponse getRentInfo(int id) {
		var rentInfo = rentInfoRepository.findById(id).orElseThrow(
				() -> new AppException(ErrorCode.RENT_INFO_NOT_EXISTED)
		);
		
		return rentInfoMapper.toRentInfoResponse(rentInfo);
	}

	public RentInfoResponse updateRentInfo(int id, RentInfoUpdateRequest request) {
		var rentInfo = rentInfoRepository.findById(id).orElseThrow(
				() -> new AppException(ErrorCode.RENT_INFO_NOT_EXISTED)
		);
		
		LocalTime startTime = request.getStartTime().plusMinutes(1);
		LocalTime endTime = request.getStartTime().plusMinutes(request.getRentTime());
		LocalDate dateNow = LocalDate.now();
		LocalTime timeNow = LocalTime.now();
		LocalTime startStopTime = LocalTime.of(23, 0);
		LocalTime endStopTime = LocalTime.of(6, 1);
		
		if (request.getRentedAt().getYear() < dateNow.getYear()) {
			throw new DateTimeException("Đặt sân thất bại, năm đặt không hợp lệ");
		} else if (request.getRentedAt().getYear() == dateNow.getYear()) {
			if (request.getRentedAt().getDayOfYear() < dateNow.getDayOfYear())
				throw new DateTimeException("Đặt sân thất bại, ngày đặt không hợp lệ");
			
			if (request.getRentedAt().getDayOfYear() == dateNow.getDayOfYear())
				if (timeNow.isAfter(startTime))
					throw new DateTimeException("Đặt sân thất bại, thời gian đặt không hợp lệ");
		}
		
		if (startTime.isAfter(startStopTime) || startTime.isBefore(endStopTime)
				|| endTime.isAfter(startStopTime) || endTime.isBefore(endStopTime))
			throw new DateTimeException("Đặt sân thất bại, sân bóng không hoạt động trong khoảng thời gian này");
		
		int stepHour = 0;
		int time = request.getRentTime();
		
		while (time > 0) {
			stepHour = request.getRentTime() - time;
			if (startTime.plusMinutes(stepHour).isAfter(startStopTime) && startTime.plusMinutes(stepHour).isAfter(endStopTime))
				throw new DateTimeException("Đặt sân thất bại, sân bóng không hoạt động trong khoảng thời gian này");
			time -= 60;
		}
		
		int initCount = request.getTypePitch() == 5 ? 1
				: request.getTypePitch() == 7 ? 3
				: request.getTypePitch() == 11 ? 9 : 1;
		
		AtomicInteger countByStartTime = new AtomicInteger(initCount);
		rentInfoRepository
				.findByPitchIdAndRentedAtEqualsAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
						rentInfo.getPitch().getId(),
						request.getRentedAt(),
						startTime.plusSeconds(1),
						startTime.plusSeconds(1))
				.forEach(_rentInfo -> {
					if (_rentInfo.getTypePitch() == 5)
						countByStartTime.addAndGet(1);
					else if (_rentInfo.getTypePitch() == 7)
						countByStartTime.addAndGet(3);
				});
		System.out.println("Count rent-info by start time: " + countByStartTime);
		
		AtomicInteger countByEndTime = new AtomicInteger(initCount);
		rentInfoRepository
				.findByPitchIdAndRentedAtEqualsAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
						rentInfo.getPitch().getId(),
						request.getRentedAt(),
						endTime.minusSeconds(1),
						endTime.minusSeconds(1))
				.forEach(_rentInfo -> {
					if (_rentInfo.getTypePitch() == 5)
						countByEndTime.addAndGet(1);
					else if (_rentInfo.getTypePitch() == 7)
						countByEndTime.addAndGet(3);
				});
		System.out.println("Count rent-info by end time: " + countByStartTime);
		
		AtomicInteger countByStartTimeBetween = new AtomicInteger(initCount);
		rentInfoRepository
				.findByPitchIdAndRentedAtEqualsAndStartTimeBetween(rentInfo.getPitch().getId(), request.getRentedAt(), startTime, endTime)
				.forEach(_rentInfo -> {
					if (_rentInfo.getTypePitch() == 5)
						countByStartTimeBetween.addAndGet(1);
					else if (_rentInfo.getTypePitch() == 7)
						countByStartTimeBetween.addAndGet(3);
				});
		System.out.println("Count rent-info by start time between: " + countByStartTimeBetween);
		
		AtomicInteger countByEndTimeBetween = new AtomicInteger(initCount);
		rentInfoRepository
				.findByPitchIdAndRentedAtEqualsAndEndTimeBetween(rentInfo.getPitch().getId(), request.getRentedAt(), startTime, endTime)
				.forEach(_rentInfo -> {
					if (_rentInfo.getTypePitch() == 5)
						countByEndTimeBetween.addAndGet(1);
					else if (_rentInfo.getTypePitch() == 7)
						countByEndTimeBetween.addAndGet(3);
				});
		System.out.println("Count rent-info by end time between: " + countByEndTimeBetween);
		
		if ( countByStartTime.get() > rentInfo.getPitch().getTotal() || countByEndTime.get() > rentInfo.getPitch().getTotal() )
			throw new DateTimeException("Đặt sân thất bại, không còn sân trống trong khoảng thời gian này");
		else if ( countByStartTimeBetween.get() > rentInfo.getPitch().getTotal() || countByEndTimeBetween.get() > rentInfo.getPitch().getTotal() )
			throw new DateTimeException("Đặt sân thất bại, không còn sân trống trong khoảng thời gian này");
		
		rentInfoMapper.updateRentInfo(rentInfo, request);
		rentInfo.setStartTime(startTime);
		rentInfo.setEndTime(endTime);
		int total = (int) (((float) request.getRentTime() / 60) * (rentInfo.getPitch().getPrice() * rentInfo.getPaymentMethod().getPriceRate()));
		rentInfo.setTotal(rentInfo.getTypePitch() == 5 ? total
				: rentInfo.getTypePitch() == 7 ? total * 3
				: rentInfo.getTypePitch() == 11 ? total * 9 : total);
		
		return rentInfoMapper.toRentInfoResponse(rentInfoRepository.save(rentInfo));
	}
	
	public void deleteRentInfo(int id) {
		if (!rentInfoRepository.existsById(id))
			throw new AppException(ErrorCode.RENT_INFO_NOT_EXISTED);
		
		rentInfoRepository.deleteById(id);
	}

	public void exchangeRentInfoToBill(int id) {
		var rentInfo = rentInfoRepository.findById(id).orElseThrow(
				() -> new AppException(ErrorCode.RENT_INFO_NOT_EXISTED)
		);
		
		Bill bill = Bill.builder()
				.email(rentInfo.getEmail())
				.phoneNumber(rentInfo.getPhoneNumber())
				.firstName(rentInfo.getFirstName())
				.lastName(rentInfo.getLastName())
				.createdAt(LocalDate.now())
				.rentedAt(rentInfo.getRentedAt())
				.startTime(rentInfo.getStartTime())
				.endTime(rentInfo.getEndTime())
				.total(rentInfo.getTotal())
				.pitch(rentInfo.getPitch())
				.user(rentInfo.getUser())
				.paymentMethod(rentInfo.getPaymentMethod())
				.build();
		
		rentInfo.setIsDone(true);
		rentInfoRepository.save(rentInfo);
		billService.createBill(bill);
	}
}
