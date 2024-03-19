package com.fpoly.security.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fpoly.utils.ToastUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
	@Autowired
	ToastUtil toastUtil;
	@Autowired
	HttpSession session;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		toastUtil.setAlertMsg(false, "logoutError", "Đăng nhập thất bại, tài khoản hoặc mật khẩu không chính xác");
		System.out.println("Login failure handler is called!");
		response.sendRedirect("/account/login");
	}

}
