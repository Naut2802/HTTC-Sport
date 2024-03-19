package com.fpoly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpoly.model.DanhGia;
import com.fpoly.model.HoaDon;
import com.fpoly.model.San;
import com.fpoly.model.ThongTinDatSan;
import com.fpoly.model.User;
import com.fpoly.model.Vip;
import com.fpoly.service.DanhGiaService;
import com.fpoly.service.HoaDonService;
import com.fpoly.service.SanService;
import com.fpoly.service.UserService;
import com.fpoly.utils.ToastUtil;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("user")
public class UserController {
	@Autowired
	ServletContext application;
	@Autowired
	HttpServletRequest request;
	@Autowired
	HttpServletResponse response;
	@Autowired
	HttpSession session;
	@Autowired
	UserService userService;
	@Autowired
	HoaDonService hoaDonService;
	@Autowired
	SanService sanService;
	@Autowired
	DanhGiaService danhGiaService;
	@Autowired
	ToastUtil toastUtil;
	
	@GetMapping("bill")
	public String bill(Model model) {
		User user = (User) session.getAttribute("user");
		List<HoaDon> hoadon = userService.findbyId(user.getUsername());
		model.addAttribute("bill",hoadon);
		System.out.println(hoadon.size());
		session.setAttribute("rq", "user/bill.html");
		return "index";
	}
	
	@GetMapping("bill/rate")
	public String rate(Model model, @RequestParam("maHd") Integer maHd, @RequestParam("rate") Integer rate) {
		User user = (User) session.getAttribute("user");
		HoaDon hoadon = hoaDonService.findById(maHd);
		San san = hoadon.getSan();
		String nhanXet = request.getParameter("nhanXet"+hoadon.getMaHd());
		DanhGia danhGia = new DanhGia(null, rate, nhanXet, san, user);
		hoadon.setIsRate(true);
		hoaDonService.save(hoadon);
		danhGiaService.save(danhGia);
		
		toastUtil.setAlertMsg(true, "danhGiaSuccess", "Gửi đánh giá thành công");
		return "redirect:/user/bill";
	}
	
	@GetMapping("ttDatSanUser")
	public String ttDatSanUser() {
		User user = (User) session.getAttribute("user");
		List<ThongTinDatSan> ttDatSan = userService.findByUser(user.getUsername());
		System.out.println(ttDatSan.size());
		session.setAttribute("ttDatSan", ttDatSan);
		session.setAttribute("rq", "user/ttDatSanUser.html");
		return "index";
	}
}
