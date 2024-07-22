package com.fpoly.httc_sport.service;

import com.fpoly.httc_sport.dto.request.PayOSRequest;
import com.fpoly.httc_sport.dto.request.VietQrRequest;
import com.fpoly.httc_sport.dto.response.PayOSResponse;
import com.fpoly.httc_sport.dto.response.VietQrResponse;
import com.fpoly.httc_sport.repository.PaymentMethodRepository;
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
	
	public PayOSResponse generatePayOS() throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
		PayOSRequest request = PayOSRequest.builder()
				.orderCode(12344)
				.returnUrl("http://localhost:8082/returnUrl")
				.cancelUrl("http://localhost:8082/cancelUrl")
				.buyerEmail("maousama333@gmail.com")
				.buyerName("Do Van A")
				.description("TEST PAYOS")
				.buyerPhone("091234567")
				.amount(2000)
				.build();
		
		Map<String, String> params = new TreeMap<>();
		params.put("amount", String.valueOf(request.getAmount()));
		params.put("cancelUrl", request.getCancelUrl());
		params.put("description", request.getDescription());
		params.put("orderCode", String.valueOf(request.getOrderCode()));
		params.put("returnUrl", request.getReturnUrl());
		
		Map<String, String> sortedParams = new TreeMap<>(params);
		StringBuilder dataBuilder = new StringBuilder();
		for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
			if (dataBuilder.length() > 0) {
				dataBuilder.append("&");
			}
			dataBuilder.append(entry.getKey()).append("=").append(entry.getValue());
		}
		
		String data = dataBuilder.toString();
		
		System.out.println(data);
		
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secretKeySpec = new SecretKeySpec(PAYOS_CHECKSUM_KEY.getBytes(), "HmacSHA256");
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
		String signature = hexString.toString();
		System.out.println(signature);
		
		request.setSignature(signature);
		
		return payOSClient.generateQrCode(request, PAYOS_CLIENT_ID, PAYOS_API_KEY);
	}
}
