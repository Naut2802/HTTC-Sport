package com.fpoly.httc_sport.service;

import com.fpoly.httc_sport.dto.request.PayOSRequest;
import com.fpoly.httc_sport.dto.request.VietQrRequest;
import com.fpoly.httc_sport.dto.response.PayOSResponse;
import com.fpoly.httc_sport.dto.response.VietQrResponse;
import com.fpoly.httc_sport.exception.AppException;
import com.fpoly.httc_sport.exception.ErrorCode;
import com.fpoly.httc_sport.repository.PaymentMethodRepository;
import com.fpoly.httc_sport.repository.RentInfoRepository;
import com.fpoly.httc_sport.repository.httpclient.PayOSClient;
import com.fpoly.httc_sport.repository.httpclient.VietQrClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentService {
	RentInfoRepository rentInfoRepository;
	VietQrClient vietQrClient;
	PayOSClient payOSClient;
	
	@NonFinal
	@Value("${vietqr.client-id}")
	String VIET_QR_CLIENT_ID;
	
	@NonFinal
	@Value("${bank.account.no}")
	String ACCOUNT_NO;
	
	@NonFinal
	@Value("${bank.account.name}")
	String ACCOUNT_NAME;
	
	@NonFinal
	@Value("${bank.bin}")
	String ACQID;
	
	@NonFinal
	@Value("${payos.client-id}")
	String PAYOS_CLIENT_ID;
	
	@NonFinal
	@Value("${payos.api-key}")
	String PAYOS_API_KEY;
	
	@NonFinal
	@Value("${payos.checksum-key}")
	String PAYOS_CHECKSUM_KEY;
	
	public VietQrResponse generateQr() {
		VietQrRequest request = VietQrRequest.builder()
				.accountNo(ACCOUNT_NO)
				.accountName(ACCOUNT_NAME)
				.acqId(ACQID)
				.addInfo("TEST GENERATE QR CODE")
				.amount(15000)
				.format("text")
				.template("compact2")
				.build();
		
		return vietQrClient.generateQrCode(request, VIET_QR_CLIENT_ID);
	}
	
	public String createRentPaymentLink(int rentInfoId, float deposit) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
		var rentInfo = rentInfoRepository.findById(rentInfoId).orElseThrow(
				() -> new AppException(ErrorCode.RENT_INFO_NOT_EXISTED)
		);
		
		PayOSRequest request = PayOSRequest.builder()
				.orderCode(rentInfo.getId())
				.returnUrl("http://localhost:8082/returnUrl")
				.cancelUrl("http://localhost:8082/cancelUrl")
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
		
		var response = payOSClient.generateQrCode(request, PAYOS_CLIENT_ID, PAYOS_API_KEY);
		
		return response.getData().getCheckoutUrl();
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
			if (dataBuilder.length() > 0) {
				dataBuilder.append("&");
			}
			dataBuilder.append(entry.getKey()).append("=").append(entry.getValue());
		}
		
		return dataBuilder.toString();
	}
}
