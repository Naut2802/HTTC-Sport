package com.fpoly.httc_sport.controller;

import com.fpoly.httc_sport.dto.request.RentInfoUpdateRequest;
import com.fpoly.httc_sport.dto.request.RentRequest;
import com.fpoly.httc_sport.dto.response.ApiResponse;
import com.fpoly.httc_sport.dto.response.RentInfoResponse;
import com.fpoly.httc_sport.dto.response.RentResponse;
import com.fpoly.httc_sport.service.RentInfoService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/rent-pitch")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RentController {
	RentInfoService rentInfoService;
	
	@PostMapping
	ApiResponse<RentResponse> rentPitch(@Valid @RequestBody RentRequest request) throws IOException {
		return ApiResponse.<RentResponse>builder()
				.result(rentInfoService.rentPitch(request))
				.build();
	}
	
	@PostMapping("confirm-rent")
	ApiResponse<RentResponse> confirmRent(@RequestParam("code") String code, @RequestParam("id") String id, @RequestParam("status") String status) {
		return ApiResponse.<RentResponse>builder()
				.result(rentInfoService.confirmRent(code, id, status))
				.build();
	}
	
	@PostMapping("/rent-info-to-bill/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<?> exchangeRentInfoToBill(@PathVariable int id) {
		rentInfoService.exchangeRentInfoToBill(id);
		
		return ApiResponse.builder()
				.message("Đổi thông tin đặt sân sang hóa đơn thành công")
				.build();
	}
	
	@GetMapping("{id}")
	ApiResponse<RentInfoResponse> getRentInfo(@PathVariable int id) {
		return ApiResponse.<RentInfoResponse>builder()
				.result(rentInfoService.getRentInfo(id))
				.build();
	}
	
	@GetMapping("/get-all-by-user/{userId}")
	ApiResponse<List<RentInfoResponse>> getAllRentInfoByUser(@PathVariable String userId,
				@RequestParam(defaultValue = "0") int page,
				@RequestParam(defaultValue = "5") int size) {
		return ApiResponse.<List<RentInfoResponse>>builder()
				.result(rentInfoService.getAllRentInfoByUserId(userId, page, size))
				.build();
	}
	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<List<RentInfoResponse>> getAllRentInfo(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		return ApiResponse.<List<RentInfoResponse>>builder()
				.result(rentInfoService.getAllRentInfo(page, size))
				.build();
	}
	
	@GetMapping("/get-all-by-pitch/{pitchId}")
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<List<RentInfoResponse>> getAllRentInfoByPitch(@PathVariable int pitchId,
	                                                          @RequestParam(defaultValue = "0") int page,
	                                                          @RequestParam(defaultValue = "5") int size) {
		return ApiResponse.<List<RentInfoResponse>>builder()
				.result(rentInfoService.getAllRentInfoByPitchId(pitchId, page, size))
				.build();
	}
	
	@PutMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<RentInfoResponse> updateRentInfo(@PathVariable int id, @Valid @RequestBody RentInfoUpdateRequest request) {
		return ApiResponse.<RentInfoResponse>builder()
				.result(rentInfoService.updateRentInfo(id, request))
				.build();
	}
	
	@DeleteMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<?> deleteRentInfo(@PathVariable int id) {
		rentInfoService.deleteRentInfo(id);
		
		return ApiResponse.builder()
				.message("Đã xóa thông tin đặt sân")
				.build();
	}
	
//	@GetMapping("dat-san/thanh-toan/xac-nhan-thanh-toan/{method}")
//	public RedirectView phuongThucThanhToan(@PathVariable("method") String pttt) throws UnsupportedEncodingException {
//		ThongTinDatSan ttne = (ThongTinDatSan) session.getAttribute("thongtin");
//
//		if (pttt.equals("full")) {
//			ttne.setTienCoc(ttne.getTongTien());
//			session.setAttribute("thongtin", ttne);
//		} else {
//			ttne.setTienCoc(ttne.getTongTien() * 0.35);
//			session.setAttribute("thongtin", ttne);
//		}
//
//		String vnp_Version = "2.1.0";
//		String vnp_Command = "pay";
//		String orderType = "other";
//		long amount = (long) (ttne.getTienCoc() * 100);
//		String bankCode = "NCB";
//
//		String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
//		String vnp_IpAddr = VNPayConfig.getIpAddress(request);
//
//		String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
//
//		Map<String, String> vnp_Params = new HashMap<>();
//		vnp_Params.put("vnp_Version", vnp_Version);
//		vnp_Params.put("vnp_Command", vnp_Command);
//		vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
//		vnp_Params.put("vnp_Amount", String.valueOf(amount));
//		vnp_Params.put("vnp_CurrCode", "VND");
//
//		vnp_Params.put("vnp_BankCode", bankCode);
//		vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
//		vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
//		vnp_Params.put("vnp_OrderType", orderType);
//		vnp_Params.put("vnp_Locale", "vn");
//		vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl);
//		vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
//
//		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//		String vnp_CreateDate = formatter.format(cld.getTime());
//		vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
//
//		cld.add(Calendar.MINUTE, 15);
//		String vnp_ExpireDate = formatter.format(cld.getTime());
//		vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
//
//		List fieldNames = new ArrayList(vnp_Params.keySet());
//		Collections.sort(fieldNames);
//		StringBuilder hashData = new StringBuilder();
//		StringBuilder query = new StringBuilder();
//		Iterator itr = fieldNames.iterator();
//		while (itr.hasNext()) {
//			String fieldName = (String) itr.next();
//			String fieldValue = (String) vnp_Params.get(fieldName);
//			if ((fieldValue != null) && (fieldValue.length() > 0)) {
//				// Build hash data
//				hashData.append(fieldName);
//				hashData.append('=');
//				hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
//				// Build query
//				query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
//				query.append('=');
//				query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
//				if (itr.hasNext()) {
//					query.append('&');
//					hashData.append('&');
//				}
//			}
//		}
//		String queryUrl = query.toString();
//		String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString());
//		queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
//		String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;
//		request.setAttribute("paymentUrl", paymentUrl);
//
//		return new RedirectView(paymentUrl);
//	}
}
