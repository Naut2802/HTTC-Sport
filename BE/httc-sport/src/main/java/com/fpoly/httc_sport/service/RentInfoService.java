package com.fpoly.httc_sport.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.fpoly.httc_sport.dto.response.RentPayRemainingResponse;
import com.fpoly.httc_sport.entity.RentInfo;
import com.fpoly.httc_sport.entity.User;
import com.fpoly.httc_sport.repository.*;
import com.fpoly.httc_sport.utils.Enum.BillStatusEnum;
import com.fpoly.httc_sport.utils.Enum.PaymentMethodEnum;
import com.fpoly.httc_sport.dto.request.RentRequest;
import com.fpoly.httc_sport.dto.response.RentInfoResponse;
import com.fpoly.httc_sport.dto.response.RentResponse;
import com.fpoly.httc_sport.entity.Bill;
import com.fpoly.httc_sport.entity.MailInfo;
import com.fpoly.httc_sport.exception.AppException;
import com.fpoly.httc_sport.exception.ErrorCode;
import com.fpoly.httc_sport.mapper.RentInfoMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
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
	WalletService walletService;
	
	@Transactional
	public RentResponse rentPitch(RentRequest request) {
		var pitch = pitchRepository.findById(request.getPitchId()).orElseThrow(
				() -> new AppException(ErrorCode.PITCH_NOT_EXISTED)
		);
		
		var paymentMethod = paymentMethodRepository.findByMethod(PaymentMethodEnum.valueOf(request.getPaymentMethod())).orElseThrow(
				() -> new AppException(ErrorCode.PAYMENT_METHOD_NOT_EXISTED)
		);
		
		LocalTime startTime = request.getStartTime().plusMinutes(1);
		LocalTime endTime = request.getStartTime().plusMinutes(request.getRentTime());
		LocalDate dateNow = LocalDate.now();
		LocalTime timeNow = LocalTime.now();
		LocalTime startStopTime = LocalTime.of(23, 59);
		LocalTime endStopTime = LocalTime.of(6, 1);
		
		if (request.getRentedAt().getYear() < dateNow.getYear()) {
			return RentResponse.builder().message("Đặt sân thất bại, năm đặt không hợp lệ").build();
		} else if (request.getRentedAt().getYear() == dateNow.getYear()) {
			if (request.getRentedAt().getDayOfYear() < dateNow.getDayOfYear())
				return RentResponse.builder().message("Đặt sân thất bại, ngày đặt không hợp lệ").build();
			
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
			if (startTime.plusMinutes(stepHour).isAfter(startStopTime) || startTime.plusMinutes(stepHour).isBefore(endStopTime))
				return RentResponse.builder().message("Đặt sân thất bại, sân bóng không hoạt động trong khoảng thời gian này").build();
			time -= 60;
		}
		
		int initCount = request.getTypePitch() == 5 ? 1
				: request.getTypePitch() == 7 ? 3
				: request.getTypePitch() == 11 ? 9 : 1;
		
		AtomicInteger countByStartTime = new AtomicInteger(initCount);
		rentInfoRepository
				.findByPitchIdAndRentedAtEqualsAndStartTimeLessThanEqualAndEndTimeGreaterThanEqualAndPaymentStatusTrue(
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
				.findByPitchIdAndRentedAtEqualsAndStartTimeLessThanEqualAndEndTimeGreaterThanEqualAndPaymentStatusTrue(
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
				.findByPitchIdAndRentedAtEqualsAndStartTimeBetweenAndPaymentStatusTrue(pitch.getId(), request.getRentedAt(), startTime, endTime)
				.forEach(rentInfo -> {
					if (rentInfo.getTypePitch() == 5)
						countByStartTimeBetween.addAndGet(1);
					else if (rentInfo.getTypePitch() == 7)
						countByStartTimeBetween.addAndGet(3);
				});
		System.out.println("Count rent-info by start time between: " + countByStartTimeBetween);
		
		AtomicInteger countByEndTimeBetween = new AtomicInteger(initCount);
		rentInfoRepository
				.findByPitchIdAndRentedAtEqualsAndEndTimeBetweenAndPaymentStatusTrue(pitch.getId(), request.getRentedAt(), startTime, endTime)
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
		
		var context = SecurityContextHolder.getContext();
		var user = userRepository.findByUsername(context.getAuthentication().getName())
				.orElse(userRepository.findByEmail(request.getEmail()).orElse(null));
		
		var rentInfo = rentInfoMapper.toRentInfo(request);
		
		rentInfo.setPitch(pitch);
		rentInfo.setUser(user);
		rentInfo.setStartTime(startTime);
		rentInfo.setEndTime(endTime);
		rentInfo.setPaymentMethod(paymentMethod);
		int total = getTotal(request.getRentTime(), user, rentInfo);
		rentInfo.setTotal(total);
		
		if (rentInfo.getPaymentMethod().getMethod().equals(PaymentMethodEnum.WALLET)) {
			if (user == null)
				throw new AppException(ErrorCode.PAYMENT_METHOD_INVALID);
			
			walletService.createPitchPaymentTransaction(user.getWallet(), rentInfo);
			rentInfo.setDeposit(total);
			rentInfo.setPaymentStatus(true);
		}
		
		rentInfo = rentInfoRepository.save(rentInfo);
		
		return RentResponse.builder()
				.id(rentInfo.getId())
				.deposit(rentInfo.getDeposit())
				.total(rentInfo.getTotal())
				.isRentSuccess(true)
				.message("Đặt sân thành công")
				.build();
	}
	
	public RentResponse confirmRent(String code, int orderCode, String status) {
		var paymentInfo = paymentService.getPaymentInfo(orderCode);
		
		if (paymentInfo.getData() == null)
			throw new AppException(ErrorCode.PAYMENT_NOT_EXISTED);
			
		var rentInfo = rentInfoRepository.findById(paymentInfo.getData().getOrderCode()).orElseThrow(
				() -> new AppException(ErrorCode.RENT_INFO_NOT_EXISTED)
		);
		
		if (!code.equals("00") || !status.equals("PAID")) {
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
				.isRentSuccess(true)
				.build();
	}

	public List<RentInfoResponse> getAllRentInfo(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return rentInfoRepository.findAll(pageable).stream().map(rentInfoMapper::toRentInfoResponse).toList();
	}
	
	public List<RentInfoResponse> getAllRentInfoByUserId(String userId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return rentInfoRepository.findByUserId(userId, pageable).stream().map(rentInfoMapper::toRentInfoResponse).toList();
	}
	
	public List<RentInfoResponse> getAllRentInfoByPitchId(long pitchId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return rentInfoRepository.findByPitchId(pitchId, pageable).stream().map(rentInfoMapper::toRentInfoResponse).toList();
	}
	
	public RentInfoResponse getRentInfo(int id) {
		var rentInfo = rentInfoRepository.findById(id).orElseThrow(
				() -> new AppException(ErrorCode.RENT_INFO_NOT_EXISTED)
		);
		
		return rentInfoMapper.toRentInfoResponse(rentInfo);
	}

	public RentInfoResponse updateRentInfo(int id, int rentTime) {
		var rentInfo = rentInfoRepository.findById(id).orElseThrow(
				() -> new AppException(ErrorCode.RENT_INFO_NOT_EXISTED)
		);
		
		if (rentInfo.getIsDone())
			throw new AppException(ErrorCode.RENT_INFO_EXCHANGED);
		
		if (!rentInfo.getPaymentStatus())
			throw new AppException(ErrorCode.UNPAID);
		
		LocalTime startTime = rentInfo.getStartTime();
		LocalTime endTime = rentInfo.getEndTime().plusMinutes(rentTime);
		LocalDate dateNow = LocalDate.now();
		LocalTime timeNow = LocalTime.now();
		LocalTime startStopTime = LocalTime.of(23, 59);
		LocalTime endStopTime = LocalTime.of(6, 1);
		
		if (rentInfo.getRentedAt().getYear() < dateNow.getYear()) {
			throw new DateTimeException("Đặt sân thất bại, năm đặt không hợp lệ");
		} else if (rentInfo.getRentedAt().getYear() == dateNow.getYear()) {
			if (rentInfo.getRentedAt().getDayOfYear() < dateNow.getDayOfYear())
				throw new DateTimeException("Đặt sân thất bại, ngày đặt không hợp lệ");
			
			if (rentInfo.getRentedAt().getDayOfYear() == dateNow.getDayOfYear())
				if (timeNow.isAfter(startTime))
					throw new DateTimeException("Đặt sân thất bại, thời gian đặt không hợp lệ");
		}
		
		if (startTime.isAfter(startStopTime) || startTime.isBefore(endStopTime)
				|| endTime.isAfter(startStopTime) || endTime.isBefore(endStopTime))
			throw new DateTimeException("Đặt sân thất bại, sân bóng không hoạt động trong khoảng thời gian này");
		
		int stepHour = 0;
		int time = rentTime;
		
		while (time > 0) {
			stepHour = rentTime - time;
			if (startTime.plusMinutes(stepHour).isAfter(startStopTime) || startTime.plusMinutes(stepHour).isBefore(endStopTime))
				throw new DateTimeException("Đặt sân thất bại, sân bóng không hoạt động trong khoảng thời gian này");
			time -= 60;
		}
		
		int initCount = 0;
		
		AtomicInteger countByStartTime = new AtomicInteger(initCount);
		rentInfoRepository
				.findByPitchIdAndRentedAtEqualsAndStartTimeLessThanEqualAndEndTimeGreaterThanEqualAndPaymentStatusTrue(
						rentInfo.getPitch().getId(),
						rentInfo.getRentedAt(),
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
				.findByPitchIdAndRentedAtEqualsAndStartTimeLessThanEqualAndEndTimeGreaterThanEqualAndPaymentStatusTrue(
						rentInfo.getPitch().getId(),
						rentInfo.getRentedAt(),
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
				.findByPitchIdAndRentedAtEqualsAndStartTimeBetweenAndPaymentStatusTrue(
						rentInfo.getPitch().getId(), rentInfo.getRentedAt(), startTime, endTime)
				.forEach(_rentInfo -> {
					if (_rentInfo.getTypePitch() == 5)
						countByStartTimeBetween.addAndGet(1);
					else if (_rentInfo.getTypePitch() == 7)
						countByStartTimeBetween.addAndGet(3);
				});
		System.out.println("Count rent-info by start time between: " + countByStartTimeBetween);
		
		AtomicInteger countByEndTimeBetween = new AtomicInteger(initCount);
		rentInfoRepository
				.findByPitchIdAndRentedAtEqualsAndEndTimeBetweenAndPaymentStatusTrue(
						rentInfo.getPitch().getId(), rentInfo.getRentedAt(), startTime, endTime)
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
		
		var user = rentInfo.getUser();
		
		int total = getTotal(rentTime, user, rentInfo);
		
		rentInfo.setStartTime(startTime);
		rentInfo.setEndTime(endTime);
		rentInfo.setTotal(rentInfo.getTotal() + total);
		
		return rentInfoMapper.toRentInfoResponse(rentInfoRepository.save(rentInfo));
	}
	
	private int getTotal(int rentTime, User user, RentInfo rentInfo) {
		float discountRate = user != null ? user.getVip().getDiscountRate() : 1;
		int price = (int) (((float) rentTime / 60) * rentInfo.getPitch().getPrice());
		int total = rentInfo.getTypePitch() == 5 ? price
				: rentInfo.getTypePitch() == 7 ? price * 3
				: rentInfo.getTypePitch() == 11 ? price * 9 : price;
		total = (int) (total * rentInfo.getPaymentMethod().getPriceRate() * discountRate);
		
		return total;
	}
	
	public String deleteRentInfo(int id) {
		var rentInfo = rentInfoRepository.findById(id).orElseThrow(
				() -> new AppException(ErrorCode.RENT_INFO_NOT_EXISTED)
		);
		
		if (rentInfo.getPaymentStatus())
			return "Không thể xóa thông tin đặt sân này (Đã thanh toán cọc)";
		else {
			rentInfoRepository.delete(rentInfo);
			return "Xóa thông tin đặt sân thành công";
		}
	}

	public RentPayRemainingResponse payRemainingAmount(int id, String paymentMethod) throws NoSuchAlgorithmException, InvalidKeyException {
		var rentInfo = rentInfoRepository.findById(id).orElseThrow(
				() -> new AppException(ErrorCode.RENT_INFO_NOT_EXISTED)
		);
		
		if (rentInfo.getIsDone())
			throw new AppException(ErrorCode.BILL_EXISTED);
		
		if (!rentInfo.getPaymentStatus())
			throw new AppException(ErrorCode.UNPAID);
		
		if (rentInfo.getTotal() == rentInfo.getDeposit())
			throw new AppException(ErrorCode.PAID);
		
		var _paymentMethod = paymentMethodRepository.findByMethod(PaymentMethodEnum.valueOf(paymentMethod)).orElseThrow(
				() -> new AppException(ErrorCode.PAYMENT_METHOD_NOT_EXISTED)
		);
		
		if (_paymentMethod.getMethod().equals(PaymentMethodEnum.QR)) {
			var orderCode = rentInfo.getId();
			
			var payOS = paymentService.createRentPayRemainingLink(orderCode, rentInfo);
			return RentPayRemainingResponse.builder()
					.id(rentInfo.getId())
					.payOSResponse(payOS)
					.message("Vui lòng thanh toán tiền sân còn lại thông qua mã QR")
					.isPaySuccess(true)
					.build();
		} else if (_paymentMethod.getMethod().equals(PaymentMethodEnum.WALLET)) {
			var user = rentInfo.getUser();
			if (user == null)
				throw new AppException(ErrorCode.RENT_INFO_PAY_REMAINING_USER_NOT_FOUND);
			var wallet = user.getWallet();
			walletService.createPitchPayRemainingAmountTransaction(wallet, rentInfo);
			
			return RentPayRemainingResponse.builder()
					.id(rentInfo.getId())
					.message("Thanh toán tiền sân còn lại thông qua ví thành công")
					.isPaySuccess(true)
					.build();
		}
		
		return RentPayRemainingResponse.builder()
				.id(rentInfo.getId())
				.message("Thanh toán thất bại")
				.isPaySuccess(false)
				.build();
	}
	
	@Transactional
	public RentResponse confirmPayRemaining(String code, int orderCode, String status) {
		var paymentInfo = paymentService.getPaymentInfo(orderCode);
		
		if (paymentInfo.getData() == null)
			throw new AppException(ErrorCode.PAYMENT_NOT_EXISTED);
		
		var rentInfo = rentInfoRepository.findById(paymentInfo.getData().getOrderCode()).orElseThrow(
				() -> new AppException(ErrorCode.RENT_INFO_NOT_EXISTED)
		);
		
		if (rentInfo.getIsDone())
			throw new AppException(ErrorCode.BILL_EXISTED);
		
		if (!rentInfo.getPaymentStatus())
			throw new AppException(ErrorCode.UNPAID);
		
		var paymentMethod = paymentMethodRepository.findByMethod(PaymentMethodEnum.QR).orElseThrow(
				() -> new AppException(ErrorCode.PAYMENT_METHOD_NOT_EXISTED)
		);
		
		if (!code.equals("00") || !status.equals("PAID")) {
			return RentResponse.builder().message("Thanh toán thất bại").build();
		}
		
		Bill bill = Bill.builder()
				.email(rentInfo.getEmail())
				.phoneNumber(rentInfo.getPhoneNumber())
				.firstName(rentInfo.getFirstName())
				.lastName(rentInfo.getLastName())
				.rentedAt(rentInfo.getRentedAt())
				.startTime(rentInfo.getStartTime())
				.endTime(rentInfo.getEndTime())
				.total(rentInfo.getTotal())
				.billStatus(BillStatusEnum.DONE.getValue())
				.typePitch(rentInfo.getTypePitch())
				.pitch(rentInfo.getPitch())
				.user(rentInfo.getUser())
				.paymentMethod(paymentMethod)
				.build();
		
		billService.createBill(bill);
		rentInfoRepository.delete(rentInfo);
		
		return RentResponse.builder()
				.id(rentInfo.getId())
				.total(rentInfo.getTotal())
				.deposit(rentInfo.getDeposit())
				.message("Thanh toán tiền sân còn lại thành công")
				.isRentSuccess(true)
				.build();
	}
	
	@Transactional
	public void exchangeRentInfoToBill(int id) {
		var rentInfo = rentInfoRepository.findById(id).orElseThrow(
				() -> new AppException(ErrorCode.RENT_INFO_NOT_EXISTED)
		);
		
		if (rentInfo.getIsDone())
			throw new AppException(ErrorCode.BILL_EXISTED);
		
		if (!rentInfo.getPaymentStatus())
			throw new AppException(ErrorCode.UNPAID);
		
		var paymentMethod = rentInfo.getPaymentMethod().getMethod().equals(PaymentMethodEnum.WALLET) ?
				rentInfo.getPaymentMethod() :
				paymentMethodRepository.findByMethod(PaymentMethodEnum.CASH).orElseThrow(
				() -> new AppException(ErrorCode.PAYMENT_METHOD_NOT_EXISTED)
		);
		
		Bill bill = Bill.builder()
				.email(rentInfo.getEmail())
				.phoneNumber(rentInfo.getPhoneNumber())
				.firstName(rentInfo.getFirstName())
				.lastName(rentInfo.getLastName())
				.rentedAt(rentInfo.getRentedAt())
				.startTime(rentInfo.getStartTime())
				.endTime(rentInfo.getEndTime())
				.total(rentInfo.getTotal())
				.billStatus(BillStatusEnum.DONE.getValue())
				.typePitch(rentInfo.getTypePitch())
				.pitch(rentInfo.getPitch())
				.user(rentInfo.getUser())
				.paymentMethod(paymentMethod)
				.build();
		
		billService.createBill(bill);
		rentInfoRepository.delete(rentInfo);
	}
}
