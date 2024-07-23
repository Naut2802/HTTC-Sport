package com.fpoly.httc_sport.service;

import com.fpoly.httc_sport.dto.request.PayOSRequest;
import com.fpoly.httc_sport.dto.response.PayOSPaymentResponse;
import com.fpoly.httc_sport.dto.response.PayOSResponse;
import com.fpoly.httc_sport.exception.AppException;
import com.fpoly.httc_sport.exception.ErrorCode;
import com.fpoly.httc_sport.repository.RentInfoRepository;
import com.fpoly.httc_sport.repository.httpclient.PayOSClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentService {
	RentInfoRepository rentInfoRepository;
	PayOSClient payOSClient;
	
	@NonFinal
	@Value("${payos.client-id}")
	String PAYOS_CLIENT_ID;
	
	@NonFinal
	@Value("${payos.api-key}")
	String PAYOS_API_KEY;
	
	@NonFinal
	@Value("${payos.checksum-key}")
	String PAYOS_CHECKSUM_KEY;
	
	public PayOSResponse createRentPaymentLink(int rentInfoId, float deposit) throws NoSuchAlgorithmException, InvalidKeyException {
		var rentInfo = rentInfoRepository.findById(rentInfoId).orElseThrow(
				() -> new AppException(ErrorCode.RENT_INFO_NOT_EXISTED)
		);
		
		PayOSRequest request = PayOSRequest.builder()
				.orderCode(rentInfo.getId())
				.returnUrl("http://localhost:3000/payment/rent/success")
				.cancelUrl("http://localhost:3000/payment/rent/error")
				.buyerEmail(rentInfo.getEmail())
				.buyerName(rentInfo.getLastName() + " " + rentInfo.getFirstName())
				.description("THANH TOAN DAT SAN " + rentInfo.getPitch().getId())
				.buyerPhone(rentInfo.getPhoneNumber())
				.amount((int) (rentInfo.getTotal() * deposit))
				.build();
		
		Map<String, String> params = new TreeMap<>();
		params.put("amount", String.valueOf(request.getAmount()));
		params.put("cancelUrl", request.getCancelUrl());
		params.put("description", request.getDescription());
		params.put("orderCode", String.valueOf(request.getOrderCode()));
		params.put("returnUrl", request.getReturnUrl());
		
		String data = generateData(params);
		String signature = generateSignature(PAYOS_CHECKSUM_KEY, data);
		
		request.setSignature(signature);
		
		return payOSClient.generateQrCode(request, PAYOS_CLIENT_ID, PAYOS_API_KEY);
	}
	
	public PayOSPaymentResponse getPaymentInfo(String id) {
		return payOSClient.getPaymentInfo(id, PAYOS_CLIENT_ID, PAYOS_API_KEY);
	}
	
	private String generateSignature(String checksum_key, String data) throws NoSuchAlgorithmException, InvalidKeyException {
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secretKeySpec = new SecretKeySpec(checksum_key.getBytes(), "HmacSHA256");
		sha256_HMAC.init(secretKeySpec);
		byte[] hash = sha256_HMAC.doFinal(data.getBytes());
		
		StringBuilder hexString = new StringBuilder(2 * hash.length);
		for (byte b : hash) {
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		
		return hexString.toString();
	}
	
	private String generateData(Map<String, String> params) {
		Map<String, String> sortedParams = new TreeMap<>(params);
		StringBuilder dataBuilder = new StringBuilder();
		for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
			if (!dataBuilder.isEmpty()) {
				dataBuilder.append("&");
			}
			dataBuilder.append(entry.getKey()).append("=").append(entry.getValue());
		}
		
		return dataBuilder.toString();
	}
}
