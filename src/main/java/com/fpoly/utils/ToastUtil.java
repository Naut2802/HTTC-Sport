package com.fpoly.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSession;



@Component
public class ToastUtil {
	@Autowired
	HttpSession session;
	
	public void setAlertMsg(boolean isSuccess, String msgTitle, String msg) {
		String showToastScript = "{";
		showToastScript += "\"isSuccess\": " + isSuccess + ",";
		showToastScript += "\"message\": \"" + msg + "\"";
		showToastScript += "}";
		session.setAttribute("showToastScript", showToastScript);
	}
}
