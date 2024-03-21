package com.fpoly.controller;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fpoly.model.Role;
import com.fpoly.model.San;
import com.fpoly.model.User;
import com.fpoly.model.Vip;
import com.fpoly.security.CustomUserDetails;
import com.fpoly.security.oauth2.CustomOAuth2User;
import com.fpoly.service.MailerService;
import com.fpoly.service.SanService;
import com.fpoly.service.UserService;
import com.fpoly.utils.CookieUtils;
import com.fpoly.utils.ToastUtil;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("account")
public class AccountController {
	@Autowired
	HttpServletRequest request;
	@Autowired
	HttpServletResponse response;
	@Autowired
	HttpSession session;
	@Autowired
	UserService userService;
	@Autowired
	SanService sanService;
	@Autowired
	ToastUtil toastUtil;
	@Autowired
	MailerService mailer;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@GetMapping("login")
	public String getLogin() throws UnsupportedEncodingException {
		if(session.getAttribute("user") != null) {
			return "redirect:/home";
		}
		session.setAttribute("rq", "account/login.html");
		return "index";
	}

	@GetMapping("loginProcessing")
	public String loginProcessing(Principal principal, Model model) throws UnsupportedEncodingException {
		User user = null;
		if(principal instanceof Authentication authentication) {
			if(authentication.getPrincipal() instanceof CustomUserDetails userDetails)
				user = userDetails.getUser();
			if(authentication.getPrincipal() instanceof CustomOAuth2User oAuth2User)
				user = oAuth2User.getUser();
		}

		session.setAttribute("user", user);
		assert user != null;
		if(user.checkRole()) {
			toastUtil.setAlertMsg(true, "loginSuccess", "Đăng nhập thành công với quyền Admin");
			return "redirect:/admin";
		} else {
			toastUtil.setAlertMsg(true, "loginSuccess", "Đăng nhập thành công");
			return "redirect:/home";
		}
	}
	
	@GetMapping("logoutProcessing")
	public String logoutProcessing(Model model) {
		if(session.getAttribute("user") != null) {
			toastUtil.setAlertMsg(true, "logoutSuccess", "Đăng xuất thành công");
			session.removeAttribute("user");
		}
		return "redirect:/home";
	}
	
	@GetMapping("register")
	public String getRegister(Model model) {
		model.addAttribute("user", new User());
		session.setAttribute("rq", "account/register.html");
		return "index";
	}
	
	@PostMapping("register")
	public String doRegister(@ModelAttribute("user") User user, Model model) {
		if(userService.register(user)) {
			toastUtil.setAlertMsg(true, "registerSuccess", "Đăng ký tài khoản thành công");
			return "redirect:/account/login";
		}

		toastUtil.setAlertMsg(false, "registerError", "Đăng ký tài khoản thất bại, Username hoặc Email đã được sử dụng");
		return "redirect:/account/register";
	}
	@GetMapping("info")
	public String getInfoVip(Model model) {
		session.setAttribute("rq", "account/info.html");
		List<Vip> bacVip = userService.findAll();
		model.addAttribute("bacVip",bacVip);
		return "index";
	}
	
	@PostMapping("updateProfile")
	public String doUpdateProfife(User user, Model model) {
		user = userService.edit2(user.getUsername());	
		user.setFirstName(request.getParameter("firstName"));
		user.setLastName(request.getParameter("lastName"));
		user.setEmail(request.getParameter("email"));
		user.setPhoneNumber(request.getParameter("phoneNumber"));
		userService.save(user);
		toastUtil.setAlertMsg(true, "updateProfileSuccess", "Cập nhập thông tin thành công");
		session.setAttribute("user", user);
		return "redirect:/account/info";
	}
	
	@GetMapping("changePassword")
	public String getChangePassword() {
		session.setAttribute("rq", "account/changePassword.html");
		return "index";
	}
	
	@PostMapping("changePassword")
	public String changePassword(@RequestParam("username") String username, Model model) {
		User user = userService.edit2(username);
		String old_password = request.getParameter("old-password");

		if (passwordEncoder.matches(old_password,user.getPassword())) {
			String new_password = request.getParameter("new-password");
			String cf_password = request.getParameter("confirm-password");
			if (new_password.equalsIgnoreCase(cf_password)) {
				user.setPassword(passwordEncoder.encode(new_password));
				userService.save(user);
				System.out.println(user.getPassword());
				toastUtil.setAlertMsg(true, "false", "Đổi Mật Khẩu Thành Công");
			}else {
				toastUtil.setAlertMsg(false, "false", "Xác Nhận Mật Khẩu Không Chính Xác");
			}
		}else {
			toastUtil.setAlertMsg(false, "false", "Mật Khẩu không chính xác ");
		}
		return "redirect:/account/changePassword";
	}

	@GetMapping("forgotPassword")
	public String getForgotPassword() {
		session.setAttribute("rq", "account/forgotPassword.html");
		return "index";
	}
	
	@PostMapping("forgotPassword-2")
	public String doForgotPassword_1(@RequestParam("username") String username, Model model) {
		User _user = userService.edit2(username);
		
		if(_user != null) {
			session.setAttribute("userFind", _user);
			toastUtil.setAlertMsg(true, "true", "Username hợp lệ");
			session.setAttribute("rq", "account/forgotPassword-2.html");
			return "index";
		}
		
		toastUtil.setAlertMsg(false, "false", "Username không chính xác");
		return "redirect:/account/forgotPassword";
	}
	
	@PostMapping("forgotPassword-3")
	public String doForgotPassword_2(@RequestParam("email") String email, Model model) {
		User userFind = (User) session.getAttribute("userFind");
		
		if(userFind.getEmail().equals(email)) {
			toastUtil.setAlertMsg(true, "true", "Email hợp lệ, vui lòng nhập mã OTP đã gửi qua Email đăng ký");
			int otp = new Random().nextInt(123456);
			mailer.queue(userFind.getEmail(), "Reset mật khẩu thông qua OTP !", "Vui lòng nhập mã OTP sau đây để có thể reset mật khẩu: " + otp, "", "");
			session.setAttribute("otp", otp);
			
			session.setAttribute("rq", "account/confirm-otp.html");
			return "index";
		}

		toastUtil.setAlertMsg(false, "false", "Email của tài khoản không chính xác");
		session.setAttribute("rq", "account/forgotPassword-2.html");
		return "index";
	}
	
	@PostMapping("confirm-otp")
	public String confirmOtp(@RequestParam("confirmOtp") Integer _otp, Model model) {
		int otp = (int) session.getAttribute("otp");
		System.out.println(otp);
		System.out.println(_otp);
		if(otp == _otp) {
			toastUtil.setAlertMsg(true, "true", "Mã OTP hợp lệ !");
			session.setAttribute("rq", "account/forgotPassword-3.html");
			session.removeAttribute("otp");
			return "index";
		}

		toastUtil.setAlertMsg(false, "true", "Mã OTP không hợp lệ !");
		session.removeAttribute("otp");
		session.setAttribute("rq", "account/forgotPassword-2");
		return "index";
	}
	
	@PostMapping("resetPassword")
	public String doResetPassword(@RequestParam("newPassword") String newPassword, @RequestParam("reNewPassword") String reNewPassword, Model model) throws MessagingException {
		User userFind = (User) session.getAttribute("userFind");
		if(newPassword.equals(reNewPassword)) {
			userFind.setPassword(passwordEncoder.encode(newPassword));
			userService.save(userFind);
			mailer.queue(userFind.getEmail(), "Email cảnh báo bảo mật !", "Mật khẩu của bạn vừa được thay đổi !", "", "");
			toastUtil.setAlertMsg(true, "true", "Reset mật khẩu thành công");
			session.removeAttribute("userFind");
			return "redirect:/account/login";
		}
		
		toastUtil.setAlertMsg(false, "false", "Mật khẩu xác nhận phải trùng với mật khẩu mới");
		session.setAttribute("rq", "account/forgotPassword-3");
		return "index";
	}
}
