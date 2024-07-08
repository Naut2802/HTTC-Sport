package com.fpoly.httc_sport.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.fpoly.config.VNPayConfig;
import com.fpoly.httc_sport.entity.DanhGia;
import com.fpoly.httc_sport.entity.San;
import com.fpoly.httc_sport.entity.ThoiGianHoatDong;
import com.fpoly.httc_sport.entity.ThongTinDatSan;
import com.fpoly.httc_sport.entity.User;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("san-bong-da")
public class SanController {
//	@Autowired
//	ServletContext application;
//	@Autowired
//	HttpServletRequest request;
//	@Autowired
//	HttpServletResponse response;
//	@Autowired
//	HttpSession session;
//	@Autowired
//	SanService sanService;
//	@Autowired
//	ThongTinDatSanService thongtinService;
//	@Autowired
//	DanhGiaService danhGiaService;
//	@Autowired
//	ThoiGianHoatDongService thoiGianHoatDongService;
//	@Autowired
//	HoaDonService hoaDonService;
//	@Autowired
//	ToastUtil toastUtil;
//	@Autowired
//	MailerService mailer;
//
//	@GetMapping("{id}")
//	public String index(@PathVariable("id") Integer id, Model model) {
//		San san = sanService.getSan(id);
//		List<ThoiGianHoatDong> khungGio = thoiGianHoatDongService.getAll();
//		List<DanhGia> danhGia = (List<DanhGia>) danhGiaService.findById(san.getMaSan());
//		if (san != null) {
//			Float sao = 0f;
//			for (DanhGia dg : san.getListDanhGia()) {
//				sao += dg.getMocSao();
//			}
//			sao = sao / san.getListDanhGia().size();
//
//			model.addAttribute("danhGia_", danhGia);
//			model.addAttribute("san", san);
//			model.addAttribute("danhGia", sao.isNaN() ? 0 : sao);
//			session.setAttribute("rq", "san/chitietsan.html");
//			model.addAttribute("khungGio", khungGio);
//			return "index";
//		}
//		return "redirect:/home";
//	}
//
//	@GetMapping("danh-sach-san")
//	public String index() {
//		session.setAttribute("rq", "/san/dsSan.html");
//		return "index";
//	}
//
//	@GetMapping("dat-san/{id}")
//	public String datSan(@PathVariable("id") Integer id, Model model) {
//		San san = sanService.getSan(id);
//		ThongTinDatSan ttdt = new ThongTinDatSan();
//
//		if (san != null) {
//			model.addAttribute("san", san);
//			session.setAttribute("rq", "san/datSan.html");
//			return "index";
//		}
//		return "redirect:/home";
//	}
//
//	@PostMapping("dat-san/thanh-toan")
//	public String thanhToan(Model model, @RequestParam("maSan") Integer id, ThongTinDatSan thongTinDatSan) {
//		San san = sanService.getSan(id);
//		LocalDate localDate = LocalDate.now();
//		LocalTime localTime = LocalTime.now();
//		System.out.println("dat thanh cong ha");
//		boolean flag = false;
//		if (san != null) {
//			try {
//				Double tongTien = 0d;
//				Double tienCoc = 0d;
//				LocalTime time_Bd = thongTinDatSan.getThoiGianNhanSan().plusMinutes(1);
//				LocalTime time_Kt = thongTinDatSan.getThoiGianKetThuc();
//				if (thongTinDatSan.getNgayDat().getYear() > localDate.getYear()) {
//					flag = true;
//				} else if (thongTinDatSan.getNgayDat().getYear() == localDate.getYear()) {
//					if (thongTinDatSan.getNgayDat().getDayOfYear() > localDate.getDayOfYear()) {
//						flag = true;
//
//					} else if (thongTinDatSan.getNgayDat().getDayOfYear() == localDate.getDayOfYear()) {
//						if (localTime.isBefore(time_Bd))
//							flag = true;
//					}
//				}
//
//				if (thongtinService.existsByDateAndTime(thongTinDatSan.getNgayDat(), time_Bd.plusSeconds(1)) ||
//						thongtinService.existsByDateAndTime(thongTinDatSan.getNgayDat(), time_Kt.minusSeconds(1))) {
//					toastUtil.setAlertMsg(false, "loginSuccess", "Thời Gian Đặt hoặc Ngày Đá Bị Trùng");
//					return "redirect:/san-bong-da/dat-san/" + id;
//				} else if (thongtinService.existsByBetween(thongTinDatSan.getNgayDat(), time_Bd, time_Kt)) {
//					toastUtil.setAlertMsg(false, "loginSuccess", "Thời Gian Đặt hoặc Ngày Đá Bị Trùng");
//					return "redirect:/san-bong-da/dat-san/" + id;
//				}
//
//				if (flag) {
//					if (time_Bd.isBefore(time_Kt)) {
//						ThoiGianHoatDong tghd = thoiGianHoatDongService.getByTime(time_Bd);
//
//						if (tghd == null) {
//							toastUtil.setAlertMsg(false, "loginSuccess", "Thời Gian Đặt hoặc Ngày Đá Không Hợp Lệ");
//							return "redirect:/san-bong-da/dat-san/" + id;
//						}
//						System.out.println(time_Bd);
//						System.out.println(time_Kt);
//
//						if (tghd.getThoiGianKetThuc().isAfter(time_Kt.minusSeconds(1))) {
//							float minutesBetween = time_Bd.until(time_Kt, ChronoUnit.MINUTES);
//							System.out.println(minutesBetween);
//							tongTien = (san.getGiaSan() * ((minutesBetween + 1) / 60)) * tghd.getHot();
//						} else {
//							// tghd = thoiGianHoatDongService.getByTime(time_Bd);
//							float minutesBetween = time_Bd.until(tghd.getThoiGianKetThuc(), ChronoUnit.MINUTES);
//							Double _tt = (san.getGiaSan() * ((minutesBetween + 1) / 60)) * tghd.getHot();
//							tongTien += _tt;
//							tghd = thoiGianHoatDongService.getByTime(time_Kt);
//							minutesBetween = tghd.getThoiGianBatDau().until(time_Kt, ChronoUnit.MINUTES);
//							_tt = (san.getGiaSan() * ((minutesBetween + 1) / 60)) * tghd.getHot();
//
//							tongTien += _tt;
//						}
//						tongTien = (double) Math.round(tongTien);
//						tienCoc = tongTien * 0.35;
//						tienCoc = (double) Math.round(tienCoc);
//					} else
//						flag = false;
//				}
//
//				if (flag) {
//					ThongTinDatSan ttsan = new ThongTinDatSan(null, time_Bd, time_Kt, false,
//							thongTinDatSan.getNgayDat(), tongTien, tienCoc, thongTinDatSan.getGhiChu(), san,
//							session.getAttribute("user") == null ? null : (User) session.getAttribute("user"),
//							thongTinDatSan.getEmail(), thongTinDatSan.getPhoneNumber(), thongTinDatSan.getFirstName(),
//							thongTinDatSan.getLastName());
//
//					session.setAttribute("thongtin", ttsan);
//					model.addAttribute("san", san);
//					return "redirect:/san-bong-da/dat-san/thanh-toan/xac-nhan-thanh-toan";
//				} else {
//					toastUtil.setAlertMsg(false, "loginSuccess", "Thời Gian Đặt hoặc Ngày Đá Không Hợp Lệ");
//					return "redirect:/san-bong-da/dat-san/" + id;
//				}
//			} catch (Exception e) {
//				toastUtil.setAlertMsg(false, "loginSuccess", "Thời Gian Đặt Không Đúng Định Dạng");
//				return "redirect:/san-bong-da/dat-san/" + id;
//			}
//		}
//		return "redirect:/home";
//	}
//
//	@GetMapping("dat-san/thanh-toan/xac-nhan-thanh-toan")
//	public String xacNhanThanhToan() {
//		session.setAttribute("rq", "san/thanhToan.html");
//		return "index";
//	}
//
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
//
//	@GetMapping("dat-san/thanh-toan/xac-nhan-thanh-toan/kiem-tra")
//	public String checkPayment(@RequestParam("vnp_ResponseCode") String respCode) {
//		ThongTinDatSan ttSan = (ThongTinDatSan) session.getAttribute("thongtin");
//		if (respCode.equals("00")) {
//			toastUtil.setAlertMsg(true, "paymentSuccessFully", "Đặt sân thành công");
//			if (ttSan.getUser() == null) {
//				mailer.queue(ttSan.getEmail(), "Thông tin đặt sân !",
//						"<p><strong>Th&ocirc;ng tin đặt s&acirc;n của Kh&aacute;ch h&agrave;ng:&nbsp;</strong></p>\r\n"
//								+ "<p>Email: <strong>" + ttSan.getEmail() + "</strong></p>\r\n"
//								+ "<p>SĐT: <strong>" + ttSan.getPhoneNumber() + "</strong></p>\r\n"
//								+ "<p>T&ecirc;n s&acirc;n: <strong>" + ttSan.getSan().getTenSan() + "</strong></p>\r\n"
//								+ "<p>Địa chỉ s&acirc;n b&oacute;ng: <strong>" + ttSan.getSan().getDiaChi()
//								+ "</strong></p>\r\n"
//								+ "<p>Ng&agrave;y đặt: <strong>" + ttSan.getNgayDat() + "</strong></p>\r\n"
//								+ "<p>Thời gian bắt đầu: <strong>" + ttSan.getThoiGianNhanSan() + "</strong></p>\r\n"
//								+ "<p>Thời gian kết th&uacute;c: <strong>" + ttSan.getThoiGianKetThuc()
//								+ "</strong></p>",
//						"", "");
//			} else {
//				mailer.queue(ttSan.getEmail(), "Thông tin đặt sân !",
//						"<p><strong>Th&ocirc;ng tin đặt s&acirc;n của Kh&aacute;ch h&agrave;ng:&nbsp;</strong></p>\r\n"
//								+ "<p>Email: <strong>" + ttSan.getEmail() + "</strong></p>\r\n"
//								+ "<p>SĐT: <strong>" + ttSan.getPhoneNumber() + "</strong></p>\r\n"
//								+ "<p>T&ecirc;n s&acirc;n: <strong>" + ttSan.getSan().getTenSan() + "</strong></p>\r\n"
//								+ "<p>Địa chỉ s&acirc;n b&oacute;ng: <strong>" + ttSan.getSan().getDiaChi()
//								+ "</strong></p>\r\n"
//								+ "<p>Ng&agrave;y đặt: <strong>" + ttSan.getNgayDat() + "</strong></p>\r\n"
//								+ "<p>Thời gian bắt đầu: <strong>" + ttSan.getThoiGianNhanSan() + "</strong></p>\r\n"
//								+ "<p>Thời gian kết th&uacute;c: <strong>" + ttSan.getThoiGianKetThuc()
//								+ "</strong></p>\r\n"
//								+ "<p>&nbsp;</p>\r\n"
//								+ "<p>Để xem được th&ocirc;ng tin đặt s&acirc;n cụ thể: <a href=\"http://localhost:8080/user/ttDatSanUser\">Nhấn v&agrave;o đ&acirc;y</a>.</p>",
//						"", "");
//			}
//			thongtinService.save(ttSan);
//			return "redirect:/san-bong-da/" + ttSan.getSan().getMaSan();
//		}
//
//		session.removeAttribute("thongtin");
//		toastUtil.setAlertMsg(false, "paymentFailed", "Đặt sân thất bại");
//		return "redirect:/san-bong-da/" + ttSan.getSan().getMaSan();
//	}
//
//	@GetMapping("find/keywords")
//	public String findKW(Model model, @RequestParam("keywords") Optional<String> keywords) {
//		String keyword = keywords.orElse(request.getAttribute("keywords") + "");
//		request.setAttribute("keywords", keyword);
//		List<San> listSanActive = sanService.findbyKeyWords(keyword);
//		List<Float> mocSao = new ArrayList<>();
//
//		for (San san : listSanActive) {
//			Float sao = 0f;
//			for (DanhGia dg : san.getListDanhGia()) {
//				sao += dg.getMocSao();
//			}
//			sao = sao / san.getListDanhGia().size();
//
//			mocSao.add(sao.isNaN() ? 0 : sao);
//		}
//
//		session.setAttribute("listSao", mocSao);
//		session.setAttribute("listSanActive", listSanActive);
//		session.setAttribute("contextPath", request.getContextPath());
//		session.setAttribute("rq", "san/dsSan.html");
//		return "index";
//	}
//
//	@GetMapping("danh-sach-san/filter")
//	public String filter(@RequestParam("stars") String stars, @RequestParam("loai") String loaiSan) {
//		Set<San> listSanActive = loaiSan.isEmpty() && stars.isEmpty() ? new HashSet<>(sanService.getAllSanActive())
//				: sanService.filter(loaiSan.isEmpty() ? "" : loaiSan, stars.isEmpty() ? "" : stars);
//		List<Float> mocSao = new ArrayList<>();
//
//		for (San san : listSanActive) {
//			Float sao = 0f;
//			for (DanhGia dg : san.getListDanhGia()) {
//				sao += dg.getMocSao();
//			}
//			sao = sao / san.getListDanhGia().size();
//
//			mocSao.add(sao.isNaN() ? 0 : sao);
//		}
//
//		session.setAttribute("listSao", mocSao);
//		session.setAttribute("listSanActive", listSanActive);
//		return "redirect:/san-bong-da/danh-sach-san";
//	}

}
