package com.fpoly.httc_sport.controller;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import com.fpoly.httc_sport.dto.request.LoginRequest;
import com.fpoly.httc_sport.dto.request.RefreshRequest;
import com.fpoly.httc_sport.dto.request.RegisterRequest;
import com.fpoly.httc_sport.dto.response.ApiResponse;
import com.fpoly.httc_sport.dto.response.AuthenticationResponse;
import com.fpoly.httc_sport.dto.response.UserResponse;
import com.fpoly.httc_sport.event.RegistrationCompleteEvent;
import com.fpoly.httc_sport.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationController {
	AuthenticationService authenticationService;
	
	@PostMapping("sign-in")
	public ApiResponse<AuthenticationResponse> signIn(@RequestBody LoginRequest request, HttpServletResponse response) throws NoSuchAlgorithmException {
		return ApiResponse.<AuthenticationResponse>builder()
				.result(authenticationService.authenticate(request, response))
				.build();
	}
	
	@PostMapping("sign-up")
	public ApiResponse<String> signUp(@RequestBody RegisterRequest request, HttpServletRequest httpRequest) {
		String response = authenticationService.register(request, httpRequest);
		
		return ApiResponse.<String>builder()
				.message(response)
				.build();
	}
	
	@GetMapping("sign-up/resend-verification-token")
	public ApiResponse<?> resendVerificationToken(@RequestParam("token") String oldToken, HttpServletRequest request) {
		String response = authenticationService.resendVerificationToken(oldToken, request);
		
		return ApiResponse.builder()
				.message(response)
				.build();
	}
	
	@GetMapping("sign-up/verify-email")
	public ApiResponse<?> verifyEmail(@RequestParam("token") String token, HttpServletRequest request) {
		String result = authenticationService.validateEmailToken(token);
		
		if (result.contains("expired"))
			return ApiResponse.builder()
					.message("Link xác thực đã hết hạn")
					.result(token)
					.build();
		
		return ApiResponse.builder()
				.message("Account is verified now")
				.build();
	}
	
	@PutMapping("refresh-token")
	public ApiResponse<AuthenticationResponse> refreshToken(HttpServletRequest request, HttpServletResponse response, @RequestBody RefreshRequest userId) throws ParseException, JOSEException, NoSuchAlgorithmException {
		return ApiResponse.<AuthenticationResponse>builder()
				.result(authenticationService.refreshToken(request, response, userId))
				.build();
	}
	
//	@PostMapping("updateProfile")
//	public String doUpdateProfife(User user, Model model) {
//		user = userService.edit2(user.getUsername());
//		user.setFirstName(request.getParameter("firstName"));
//		user.setLastName(request.getParameter("lastName"));
//		user.setEmail(request.getParameter("email"));
//		user.setPhoneNumber(request.getParameter("phoneNumber"));
//		userService.save(user);
//		toastUtil.setAlertMsg(true, "updateProfileSuccess", "Cập nhập thông tin thành công");
//		session.setAttribute("user", user);
//		return "redirect:/account/info";
//	}
//
//	@GetMapping("changePassword")
//	public String getChangePassword() {
//		session.setAttribute("rq", "account/changePassword.html");
//		return "index";
//	}
//
//	@PostMapping("changePassword")
//	public String changePassword(@RequestParam("username") String username, Model model) {
//		User user = userService.edit2(username);
//		String old_password = request.getParameter("old-password");
//
//		if (passwordEncoder.matches(old_password,user.getPassword())) {
//			String new_password = request.getParameter("new-password");
//			String cf_password = request.getParameter("confirm-password");
//			if (new_password.equalsIgnoreCase(cf_password)) {
//				user.setPassword(passwordEncoder.encode(new_password));
//				userService.save(user);
//				System.out.println(user.getPassword());
//				toastUtil.setAlertMsg(true, "false", "Đổi Mật Khẩu Thành Công");
//			}else {
//				toastUtil.setAlertMsg(false, "false", "Xác Nhận Mật Khẩu Không Chính Xác");
//			}
//		}else {
//			toastUtil.setAlertMsg(false, "false", "Mật Khẩu không chính xác ");
//		}
//		return "redirect:/account/changePassword";
//	}
//
//	@GetMapping("forgotPassword")
//	public String getForgotPassword() {
//		session.setAttribute("rq", "account/forgotPassword.html");
//		return "index";
//	}
//
//	@PostMapping("forgotPassword-2")
//	public String doForgotPassword_1(@RequestParam("username") String username, Model model) {
//		User _user = userService.edit2(username);
//
//		if(_user != null) {
//			session.setAttribute("userFind", _user);
//			toastUtil.setAlertMsg(true, "true", "Username hợp lệ");
//			session.setAttribute("rq", "account/forgotPassword-2.html");
//			return "index";
//		}
//
//		toastUtil.setAlertMsg(false, "false", "Username không chính xác");
//		return "redirect:/account/forgotPassword";
//	}
//
//	@PostMapping("forgotPassword-3")
//	public String doForgotPassword_2(@RequestParam("email") String email, Model model) {
//		User userFind = (User) session.getAttribute("userFind");
//
//		if(userFind.getEmail().equals(email)) {
//			toastUtil.setAlertMsg(true, "true", "Email hợp lệ, vui lòng nhập mã OTP đã gửi qua Email đăng ký");
//			int otp = new Random().nextInt(123456);
//			mailer.queue(userFind.getEmail(), "Reset mật khẩu thông qua OTP !", "Vui lòng nhập mã OTP sau đây để có thể reset mật khẩu: " + otp, "", "");
//			session.setAttribute("otp", otp);
//
//			session.setAttribute("rq", "account/confirm-otp.html");
//			return "index";
//		}
//
//		toastUtil.setAlertMsg(false, "false", "Email của tài khoản không chính xác");
//		session.setAttribute("rq", "account/forgotPassword-2.html");
//		return "index";
//	}
//
//	@PostMapping("confirm-otp")
//	public String confirmOtp(@RequestParam("confirmOtp") Integer _otp, Model model) {
//		int otp = (int) session.getAttribute("otp");
//		System.out.println(otp);
//		System.out.println(_otp);
//		if(otp == _otp) {
//			toastUtil.setAlertMsg(true, "true", "Mã OTP hợp lệ !");
//			session.setAttribute("rq", "account/forgotPassword-3.html");
//			session.removeAttribute("otp");
//			return "index";
//		}
//
//		toastUtil.setAlertMsg(false, "true", "Mã OTP không hợp lệ !");
//		session.removeAttribute("otp");
//		session.setAttribute("rq", "account/forgotPassword-2");
//		return "index";
//	}
//
//	@PostMapping("resetPassword")
//	public String doResetPassword(@RequestParam("newPassword") String newPassword, @RequestParam("reNewPassword") String reNewPassword, Model model) throws MessagingException {
//		User userFind = (User) session.getAttribute("userFind");
//		if(newPassword.equals(reNewPassword)) {
//			userFind.setPassword(passwordEncoder.encode(newPassword));
//			userService.save(userFind);
//			mailer.queue(userFind.getEmail(), "Email cảnh báo bảo mật !", "Mật khẩu của bạn vừa được thay đổi !", "", "");
//			toastUtil.setAlertMsg(true, "true", "Reset mật khẩu thành công");
//			session.removeAttribute("userFind");
//			return "redirect:/account/login";
//		}
//
//		toastUtil.setAlertMsg(false, "false", "Mật khẩu xác nhận phải trùng với mật khẩu mới");
//		session.setAttribute("rq", "account/forgotPassword-3");
//		return "index";
//	}
}
