package com.fpoly.httc_sport.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class CookieUtils {
	public static void add(String name, String value, int hours, HttpServletResponse response)
			throws UnsupportedEncodingException {
		Cookie cookie = new Cookie(name, URLEncoder.encode(value, "UTF-8"));
		cookie.setMaxAge(hours * 60 * 60);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	public static String get(String name, String defaultValue, HttpServletRequest request)
			throws UnsupportedEncodingException {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equalsIgnoreCase(name)) {
					return URLDecoder.decode(cookie.getValue(), "UTF-8");
				}
			}
		}
		return defaultValue;
	}
}
