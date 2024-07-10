package com.fpoly.httc_sport.controller;

import java.util.List;

import com.fpoly.httc_sport.dto.request.AuthorizeUserRequest;
import com.fpoly.httc_sport.dto.request.ChangePasswordRequest;
import com.fpoly.httc_sport.dto.request.ResetPasswordRequest;
import com.fpoly.httc_sport.dto.request.UserUpdateRequest;
import com.fpoly.httc_sport.dto.response.ApiResponse;
import com.fpoly.httc_sport.dto.response.ChangePasswordResponse;
import com.fpoly.httc_sport.dto.response.UserResponse;
import com.fpoly.httc_sport.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
	UserService userService;
	
	@PatchMapping("change-password/{id}")
	public ApiResponse<ChangePasswordResponse> changePassword(@PathVariable String id, @RequestBody ChangePasswordRequest request) {
		ChangePasswordResponse response = userService.changePassword(id, request);
		
		return ApiResponse.<ChangePasswordResponse>builder()
				.message(response.getMessage())
				.result(response)
				.build();
	}
	
	@GetMapping("{userId}")
	public ApiResponse<UserResponse> getUser(@PathVariable String userId) {
		return ApiResponse.<UserResponse>builder()
				.result(userService.getUser(userId))
				.build();
	}
	
	@GetMapping("my-info")
	public ApiResponse<UserResponse> getMyInfo() {
		return ApiResponse.<UserResponse>builder()
				.result(userService.getMyInfo())
				.build();
	}
	
	@PutMapping("{userId}")
	public ApiResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
		return ApiResponse.<UserResponse>builder()
				.result(userService.updateUser(userId, request))
				.build();
	}
	
	@PutMapping("authorize/{userId}")
	public ApiResponse<UserResponse> authorizeUser(@PathVariable String userId, @RequestBody AuthorizeUserRequest request) {
		return ApiResponse.<UserResponse>builder()
				.result(userService.authorizeUser(userId, request))
				.build();
	}
	
	@DeleteMapping("{userId}")
	public ApiResponse<String> deleteUser(@PathVariable String userId) {
		userService.deleteUser(userId);
		return ApiResponse.<String>builder()
				.result("User has been deleted")
				.build();
	}
	
	@GetMapping
	public ApiResponse<List<UserResponse>> getUsers() {
		return ApiResponse.<List<UserResponse>>builder()
				.result(userService.getUsers())
				.build();
	}
	
	@GetMapping("forgot-password")
	public ApiResponse<?> checkEmail(@RequestParam("email") String email, HttpServletRequest request) {
		String response = userService.sendForgotPasswordEmail(email, request);
		
		return ApiResponse.builder()
				.message(response)
				.build();
	}
	
	@GetMapping("forgot-password/verify-token")
	public ApiResponse<?> validateToken(@RequestParam("token") String token) {
		String response = userService.validateForgotPasswordToken(token);
		
		if (response.contains("expired")) {
			return ApiResponse.builder()
					.message(response)
					.build();
			}
		
		return ApiResponse.builder()
				.message(response)
				.result(token)
				.build();
	}
	
	@PostMapping("forgot-password/reset-password")
	public ApiResponse<ChangePasswordResponse> resetPassword(@RequestParam("token") String token, @RequestBody ResetPasswordRequest request) {
		ChangePasswordResponse response = userService.resetPassword(token, request);
		
		return ApiResponse.<ChangePasswordResponse>builder()
				.message(response.getMessage())
				.result(response)
				.build();
	}
	
//	@GetMapping("bill")
//	public String bill(Model model) {
//		User user = (User) session.getAttribute("user");
//		List<HoaDon> hoadon = userService.findbyId(user.getUsername());
//		model.addAttribute("bill",hoadon);
//		System.out.println(hoadon.size());
//		session.setAttribute("rq", "user/bill.html");
//		return "index";
//	}
//
//	@GetMapping("bill/rate")
//	public String rate(Model model, @RequestParam("maHd") Integer maHd, @RequestParam("rate") Integer rate) {
//		User user = (User) session.getAttribute("user");
//		HoaDon hoadon = hoaDonService.findById(maHd);
//		San san = hoadon.getSan();
//		String nhanXet = request.getParameter("nhanXet"+hoadon.getMaHd());
//		DanhGia danhGia = new DanhGia(null, rate, nhanXet, san, user);
//		hoadon.setIsRate(true);
//		hoaDonService.save(hoadon);
//		danhGiaService.save(danhGia);
//
//		toastUtil.setAlertMsg(true, "danhGiaSuccess", "Gửi đánh giá thành công");
//		return "redirect:/user/bill";
//	}
//
//	@GetMapping("ttDatSanUser")
//	public String ttDatSanUser() {
//		User user = (User) session.getAttribute("user");
//		List<ThongTinDatSan> ttDatSan = userService.findByUser(user.getUsername());
//		System.out.println(ttDatSan.size());
//		session.setAttribute("ttDatSan", ttDatSan);
//		session.setAttribute("rq", "user/ttDatSanUser.html");
//		return "index";
//	}
}
