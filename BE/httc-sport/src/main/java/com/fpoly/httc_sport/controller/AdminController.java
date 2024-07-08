package com.fpoly.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fpoly.httc_sport.entity.DanhGia;
import com.fpoly.httc_sport.entity.HoaDon;
import com.fpoly.httc_sport.entity.Image;
import com.fpoly.httc_sport.entity.LoaiSan;
import com.fpoly.httc_sport.entity.San;
import com.fpoly.httc_sport.entity.ThoiGianHoatDong;
import com.fpoly.httc_sport.entity.ThongTinDatSan;
import com.fpoly.httc_sport.entity.User;
import com.fpoly.httc_sport.service.HoaDonService;
import com.fpoly.httc_sport.service.ImageService;
import com.fpoly.httc_sport.service.LoaiSanService;
import com.fpoly.httc_sport.service.SanService;
import com.fpoly.httc_sport.service.ThoiGianHoatDongService;
import com.fpoly.httc_sport.service.ThongTinDatSanService;
import com.fpoly.httc_sport.service.UserService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("admin")
public class AdminController {
//	@Autowired
//	ServletContext application;
//	@Autowired
//	HttpServletRequest request;
//	@Autowired
//	HttpServletResponse response;
//	@Autowired
//	HttpSession session;
//	@Autowired
//	UserService userService;
//	@Autowired
//	SanService sanService;
//	@Autowired
//	LoaiSanService loaiSanService;
//	@Autowired
//	ThongTinDatSanService thongTinService;
//	@Autowired
//	HoaDonService hoaDonService;
//	@Autowired
//	ImageService imageService;
//	@Autowired
//	SessionService sessionSer;
//	@Autowired
//	ToastUtil toastUtil;
//	@Autowired
//	ThoiGianHoatDongService thoiGianHoatDongService;
//
//	@GetMapping()
//	public String admin(Model model) {
//		List<User> listUser = userService.getAllUsers();
//		List<HoaDon> listHoaDon = (List<HoaDon>) hoaDonService.findAll();
//		List<Integer> nowYears = hoaDonService.getNowYears();
//		List<Integer> years = hoaDonService.getYears();
//
//		Map<Integer, Double> tongTienTheoThang = new HashMap<>();
//		for (int thang = 1; thang <= 12; thang++) {
//			tongTienTheoThang.put(thang, 0.0);
//		}
//
//		for (HoaDon hoaDon : listHoaDon) {
//			int thang = hoaDon.getNgayXuat().getMonthValue();
//			int nam = hoaDon.getNgayXuat().getYear();
//			if (nowYears.contains(nam)) {
//				double tongTienHoaDon = tongTienTheoThang.get(thang) + hoaDon.getTongTien();
//				tongTienTheoThang.put(thang, tongTienHoaDon);
//			}
//		}
//
//		// Chuyển dữ liệu sang list để sử dụng trong biểu đồ
//		List<Integer> thangList = new ArrayList<>(tongTienTheoThang.keySet());
//		List<Double> tongTienList = new ArrayList<>(tongTienTheoThang.values());
//
//		model.addAttribute("years", years);
//		model.addAttribute("thangList", thangList);
//		model.addAttribute("tongTienList", tongTienList);
//		model.addAttribute("listUser", listUser);
//		session.setAttribute("rq", "admin/admin-homepage.html");
//		return "index";
//	}
//
//	@GetMapping("user")
//	public String getUsers(Model model) {
//		List<User> listUser = userService.getAllUsers();
//		model.addAttribute("listUser", listUser);
//		session.setAttribute("rq", "admin/user.html");
//		return "index";
//	}
//
//	@GetMapping("ttDatSan")
//	public String getTTDatSan(Model model) {
//		List<ThongTinDatSan> ttdatSan = thongTinService.findByMaLoai();
//		model.addAttribute("ttdatSan", ttdatSan);
//		model.addAttribute("isEdit", false);
//		session.setAttribute("rq", "admin/ttDatSan.html");
//		return "index";
//	}
//
//	@GetMapping("ttDatSan/edit/{id}")
//	public String editTtdatSan(@PathVariable("id") String id, Model model) {
//		List<ThongTinDatSan> ttdatSan = thongTinService.findByMaLoai();
//		ThongTinDatSan ttsan = thongTinService.getTTSan(Long.parseLong(id));
//		model.addAttribute("ttSan", ttsan);
//		model.addAttribute("ttdatSan", ttdatSan);
//		System.out.println(ttsan);
//		model.addAttribute("isEdit", true);
//		session.setAttribute("rq", "admin/ttDatSan.html");
//		return "index";
//	}
//
//	@PostMapping("ttDatSan/update")
//	public String updateTTDatSan(@RequestParam("id") Long id, @RequestParam("email") String email,
//			@RequestParam("phoneNumber") String phoneNumber, @RequestParam("ngayDa") LocalDate ngayDa,
//			@RequestParam("ThoiGianBatDau") LocalTime ThoiGianBatDau,
//			@RequestParam("ThoiGianKetThuc") LocalTime ThoiGianKetThuc) {
//		ThongTinDatSan ttds = thongTinService.getTTSan(id);
//		San san = sanService.getSan(ttds.getSan().getMaSan());
//		LocalDate localDate = LocalDate.now();
//		LocalTime localTime = LocalTime.now();
//		boolean flag = false;
//		if (ttds.getId() != null) {
//			try {
//				double tongTien = 0d;
//				LocalTime time_Bd = ThoiGianBatDau.plusMinutes(1);
//				LocalTime time_Kt = ThoiGianKetThuc;
//				if(ngayDa.getYear() > localDate.getYear()) {
//					flag = true;
//				} else if (ngayDa.getYear() == localDate.getYear()) {
//					if (ngayDa.getDayOfYear() > localDate.getDayOfYear()) {
//						flag = true;
//					} else if (ngayDa.getDayOfYear() == localDate.getDayOfYear()) {
//						if (localTime.isBefore(time_Bd))
//							flag = true;
//					}
//				}
//
//				if (!ngayDa.equals(ttds.getNgayDat())) {
//					if (thongTinService.existsByDateAndTime(ngayDa, time_Bd.plusSeconds(1)) ||
//							thongTinService.existsByDateAndTime(ngayDa, time_Kt.minusSeconds(1))) {
//						toastUtil.setAlertMsg(false, "updateProfileSuccess", "Thời Gian Đặt hoặc Ngày Đá Bị Trùng");
//						return "redirect:/admin/ttDatSan/edit/" + id;
//					} else if (thongTinService.existsByBetween(ngayDa, time_Bd, time_Kt)) {
//						toastUtil.setAlertMsg(false, "updateProfileSuccess", "Thời Gian Đặt hoặc Ngày Đá Bị Trùng");
//						return "redirect:/admin/ttDatSan/edit/" + id;
//					}
//				}
//
//				if (flag) {
//					if (time_Bd.isBefore(time_Kt)) {
//						ThoiGianHoatDong tghd = thoiGianHoatDongService.getByTime(time_Bd);
//
//						if (tghd == null) {
//							toastUtil.setAlertMsg(false, "updateProfileSuccess",
//									"Thời Gian Đặt hoặc Ngày Đá Không Hợp Lệ");
//							return "redirect:/admin/ttDatSan/edit/" + id;
//						}
//
//						if (tghd.getThoiGianKetThuc().isAfter(time_Kt.minusSeconds(1))) {
//							float minutesBetween = time_Bd.until(time_Kt, ChronoUnit.MINUTES);
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
//					} else
//						flag = false;
//				}
//				if (flag) {
//					ttds.setEmail(email);
//					ttds.setPhoneNumber(phoneNumber);
//					ttds.setNgayDat(ngayDa);
//					ttds.setThoiGianNhanSan(time_Bd);
//					ttds.setThoiGianKetThuc(time_Kt);
//					ttds.setTongTien(tongTien);
//					thongTinService.save(ttds);
//					toastUtil.setAlertMsg(true, "updateProfileSuccess", "Cập nhập thông tin thành công");
//					return "redirect:/admin/ttDatSan";
//				} else {
//					System.out.println("sai o day");
//					toastUtil.setAlertMsg(false, "updateProfileSuccess", "Thời Gian Đặt hoặc Ngày Đá Không Hợp Lệ");
//					return "redirect:/admin/ttDatSan/edit/" + id;
//				}
//			} catch (Exception e) {
//				toastUtil.setAlertMsg(false, "updateProfileSuccess", "Thời Gian Đặt Không Đúng Định Dạng");
//				return "redirect:/admin/ttDatSan/edit/" + id;
//			}
//		}
//		return "redirect:/admin/ttDatSan";
//	}
//
//	@PostMapping("/ttDatSan/updateModal")
//	public String updateTTDatSanModal(@RequestParam("id") String id, @RequestParam("trangThai") String trangThai,
//			@RequestParam("gioThem") String gioThem) {
//		ThongTinDatSan ttdt = thongTinService.getTTSan(Long.parseLong(id));
//		San san = sanService.getSan(ttdt.getSan().getMaSan());
//		Double tongTienCu = ttdt.getTongTien();
//		session.setAttribute("tienThem", tongTienCu);
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
//		if (!gioThem.isEmpty()) {
//			try {
//				Double tongTienMoi = 0d;
//				LocalTime time_Plus = LocalTime.parse(gioThem, formatter);
//				LocalTime tgktN = ttdt.getThoiGianKetThuc().plusHours(time_Plus.getHour())
//						.plusMinutes(time_Plus.getMinute());
//
//				if (thongTinService.existsByDateAndTime(ttdt.getNgayDat(), tgktN)) {
//					System.out.println(time_Plus);
//					System.out.println(ttdt.getNgayDat());
//					System.out.println(ttdt.getThoiGianNhanSan());
//					System.out.println(tgktN);
//					toastUtil.setAlertMsg(false, "updateProfileSuccess", "Thời Gian Kết Thúc Đã Bị Trùng");
//					return "redirect:/admin/ttDatSan";
//				}
//
//				if (ttdt.getThoiGianNhanSan().isBefore(tgktN)) {
//					ThoiGianHoatDong tghd = thoiGianHoatDongService.getByTime(ttdt.getThoiGianNhanSan());
//
//					if (tghd == null) {
//						toastUtil.setAlertMsg(false, "updateProfileSuccess", "Thời Gian Đặt hoặc Ngày Đá Không Hợp Lệ");
//						return "redirect:/admin/ttDatSan/edit/" + id;
//					}
//
//					if (tghd.getThoiGianKetThuc().isAfter(tgktN.minusSeconds(1))) {
//						float minutesBetween = ttdt.getThoiGianNhanSan().until(tgktN, ChronoUnit.MINUTES);
//						tongTienMoi = (san.getGiaSan() * ((minutesBetween + 1) / 60)) * tghd.getHot();
//					} else {
//						// tghd = thoiGianHoatDongService.getByTime(time_Bd);
//						float minutesBetween = ttdt.getThoiGianNhanSan().until(tghd.getThoiGianKetThuc(),
//								ChronoUnit.MINUTES);
//						Double _tt = (san.getGiaSan() * ((minutesBetween + 1) / 60)) * tghd.getHot();
//						tongTienMoi += _tt;
//						tghd = thoiGianHoatDongService.getByTime(tgktN);
//						minutesBetween = tghd.getThoiGianBatDau().until(tgktN, ChronoUnit.MINUTES);
//						_tt = (san.getGiaSan() * ((minutesBetween + 1) / 60)) * tghd.getHot();
//
//						tongTienMoi += _tt;
//					}
//					tongTienMoi = (double) Math.round(tongTienMoi);
//				}
//				ttdt.setThoiGianKetThuc(tgktN);
//				ttdt.setTongTien(tongTienMoi);
//				thongTinService.save(ttdt);
//			} catch (Exception e) {
//				toastUtil.setAlertMsg(false, "updateProfileSuccess", "Thời Gian Thêm Không Đúng Định Dạng");
//				return "redirect:/admin/ttDatSan/edit/" + id;
//			}
//		}
//		// san.setTrangThaiHoatDong(TrangThai.equals("1") ? "Đang Đá" : "Sân Trống");
//		ttdt.setTrangThai(trangThai.equals("2") ? true : false);
//		if (ttdt.getTrangThai()) {
//			HoaDon hd = new HoaDon(null, ttdt.getNgayDat(), ttdt.getTongTien(), ttdt.getThoiGianNhanSan(),
//					ttdt.getThoiGianKetThuc(), false, san, ttdt.getUser() == null ? null : ttdt.getUser(),
//					ttdt.getEmail(), ttdt.getPhoneNumber());
//			hoaDonService.save(hd);
//		}
//		toastUtil.setAlertMsg(true, "updateProfileSuccess", "Cập nhập thông tin thành công");
//		return "redirect:/admin/ttDatSan";
//	}
//
//	@GetMapping("san")
//	public String getSan(Model model) {
//		List<San> listSan = sanService.getAllSan();
//
//		List<Float> mocSao = new ArrayList<>();
//
//		for (San san : listSan) {
//			Float sao = 0f;
//			for (DanhGia dg : san.getListDanhGia()) {
//				sao += dg.getMocSao();
//			}
//			sao = sao / san.getListDanhGia().size();
//
//			mocSao.add(sao.isNaN() ? 0 : sao);
//		}
//
//		model.addAttribute("listSao", mocSao);
//		model.addAttribute("listSan", listSan);
//		System.out.println(listSan.size() + "abc");
//		session.setAttribute("rq", "admin/san.html");
//		return "index";
//	}
//
//	@GetMapping("san/add")
//	public String getAddSan(Model model) {
//		List<San> listSan = sanService.getAllSan();
//		List<LoaiSan> listLoaiSan = loaiSanService.getAllLoaiSan();
//
//		model.addAttribute("listLoaiSan", listLoaiSan);
//		model.addAttribute("listSan", listSan);
//		System.out.println(listSan.size());
//		session.setAttribute("rq", "admin/addSan.html");
//		return "index";
//	}
//
//	@GetMapping("san/edit/{id}")
//	public String edit(Model model, @PathVariable("id") Integer id) {
//		List<San> listSan = sanService.getAllSan();
//		List<LoaiSan> listLoaiSan = loaiSanService.getAllLoaiSan();
//		San san = sanService.getSan(id);
//		List<Image> listHinhAnh = san.getListHinhAnh();
//
//		model.addAttribute("san", san);
//		model.addAttribute("isEdit", true);
//		model.addAttribute("listHinhAnh", listHinhAnh);
//		model.addAttribute("listLoaiSan", listLoaiSan);
//		model.addAttribute("listSan", listSan);
//		session.setAttribute("rq", "admin/addSan.html");
//		return "index";
//	}
//
//	@GetMapping("san/delete/{id}")
//	public String delete(@PathVariable("id") Integer id) {
//		sanService.deleteSan(id);
//
//		return "redirect:/admin/san/add";
//	}
//
//	@PostMapping("san/create")
//	public String create(San item, @RequestParam("imgs") MultipartFile[] imgs) {
//		sanService.save(item);
//		if (imgs.length > 0) {
//			for (int i = 0; i < imgs.length; i++) {
//				try {
//					String realPath = application.getRealPath("/WEB-INF/imgs/");
//					File uploadDir = new File(realPath);
//
//					if (!uploadDir.exists()) {
//						uploadDir.mkdirs();
//					}
//
//					File uploadedFile = new File(uploadDir.getAbsolutePath(), imgs[i].getOriginalFilename());
//					imgs[i].transferTo(uploadedFile);
//
//					Image image = new Image(imgs[i].getOriginalFilename(), item);
//					imageService.save(image);
//				} catch (Exception e) {
//				}
//			}
//		}
//
//		System.out.println("chạy vô create");
//		return "redirect:/admin/san/add";
//	}
//
//	@PostMapping("san/update")
//	public String update(San item, @RequestParam("imgs") MultipartFile[] imgs) {
//		sanService.save(item);
//		if (imgs.length > 0 && !imgs[0].getOriginalFilename().isBlank()) {
//			imageService.deleteAll(item.getMaSan());
//			for (int i = 0; i < imgs.length; i++) {
//				try {
//					String realPath = application.getRealPath("/WEB-INF/imgs/");
//					File uploadDir = new File(realPath);
//
//					if (!uploadDir.exists()) {
//						uploadDir.mkdirs();
//					}
//
//					File uploadedFile = new File(uploadDir.getAbsolutePath(), imgs[i].getOriginalFilename());
//					imgs[i].transferTo(uploadedFile);
//
//					Image image = new Image(imgs[i].getOriginalFilename(), item);
//					imageService.save(image);
//				} catch (Exception e) {
//				}
//			}
//		}
//
//		System.out.println("chạy vô update");
//		return "redirect:/admin/san/add";
//	}
//
//	@GetMapping("san/reset")
//	public String clearForm() {
//
//		System.out.println("chạy vô update");
//		return "redirect:/admin/san/add";
//	}
//
//	@GetMapping("/xuat-hd")
//	public String xuatHDon(Model model, String folderPath) throws IOException {
//		folderPath = application.getContextPath() + "Downloads";
//		List<HoaDon> listHd = (List<HoaDon>) session.getAttribute("hoaDon");
//		hoaDonService.xuatHD(folderPath, listHd);
//		session.setAttribute("rq", "admin/hoaDon.html");
//		toastUtil.setAlertMsg(true, "xuatExcel", "Đã Xuất File Excel");
//		return "redirect:/admin/hoaDon";
//	}
//
//	@GetMapping("hoaDon")
//	public String getHoaDon(Model model, @ModelAttribute("listHd") Optional<ArrayList<HoaDon>> listHoaDon) {
//		List<HoaDon> listHd = listHoaDon.orElse(new ArrayList<>(hoaDonService.getMonthHoaDonNow()));
//		double tongTien = 0;
//		for (HoaDon _hoaDon : listHd) {
//			tongTien += _hoaDon.getTongTien();
//		}
//		model.addAttribute("tongTien", tongTien);
//		model.addAttribute("hoaDon", listHd);
//		session.setAttribute("rq", "admin/hoaDon.html");
//		return "index";
//	}
//
//	@PostMapping("hoaDon/searchDate")
//	public String getHoaDonDate(Model model, @RequestParam("startDate") Optional<LocalDate> startDate,
//	                            @RequestParam("endDate") Optional<LocalDate> endDate, RedirectAttributes params) {
//		if (startDate.isPresent() && endDate.isPresent()) {
//			List<HoaDon> items = hoaDonService.getHoaDonDate(startDate.get(), endDate.get());
//			Optional<List<HoaDon>> optionalHoaDonList = Optional.of(items);
//
//			params.addFlashAttribute("listHd", optionalHoaDonList);
//
//			return "redirect:/admin/hoaDon";
//		} else {
//			// Xử lý trường hợp khi thiếu tham số startDate hoặc endDate
//			return "error";
//		}
//	}
//
//	@PostMapping("chart")
//	public String chart(Model model, @RequestParam("year") Optional<Integer> yr,
//			@RequestParam("p") Optional<Integer> p) {
//		List<HoaDon> listHoaDon = hoaDonService.findAll();
//		List<Integer> years = hoaDonService.getYears();
//
//		List<User> listUser = userService.getAllUsers();
//		// Lấy giá trị năm từ tham số yr
//		// Nếu không có giá trị, chọn năm hiện tại hoặc một giá trị mặc định khác
//		// Ở đây, mặc định là năm hiện tại
//		Integer selectedYear = yr.orElse(sessionSer.get("year"));
//		if (selectedYear == null) {
//			selectedYear = 2024;
//		}
//
//		// Cập nhật dữ liệu cho biểu đồ với giá trị năm đã chọn
//		Map<Integer, Double> tongTienTheoThang = new HashMap<>();
//		for (int thang = 1; thang <= 12; thang++) {
//			tongTienTheoThang.put(thang, 0.0);
//		}
//
//		for (HoaDon hoaDon : listHoaDon) {
//			int thang = hoaDon.getNgayXuat().getMonthValue();
//			int nam = hoaDon.getNgayXuat().getYear();
//			if (selectedYear.equals(nam)) {
//				double tongTienHoaDon = tongTienTheoThang.get(thang) + hoaDon.getTongTien();
//				tongTienTheoThang.put(thang, tongTienHoaDon);
//			}
//		}
//
//		// Chuyển dữ liệu sang list để sử dụng trong biểu đồ
//		List<Integer> thangList = new ArrayList<>(tongTienTheoThang.keySet());
//		List<Double> tongTienList = new ArrayList<>(tongTienTheoThang.values());
//
//		// Gửi dữ liệu mới về trang
//		model.addAttribute("selectedYear", selectedYear);
//		model.addAttribute("listUser", listUser);
//		model.addAttribute("years", years);
//		model.addAttribute("thangList", thangList);
//		model.addAttribute("tongTienList", tongTienList);
//		session.setAttribute("rq", "admin/admin-homepage.html");
//		// Chuyển về trang chính
//		return "index";
//	}
}
