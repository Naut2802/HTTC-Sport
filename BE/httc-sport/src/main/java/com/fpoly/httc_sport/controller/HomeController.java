package com.fpoly.httc_sport.controller;

import org.springframework.stereotype.Controller;

@Controller
public class HomeController {
//	@Autowired
//	HttpServletRequest request;
//	@Autowired
//	HttpSession session;
//	@Autowired
//	SanService sanService;
//
//	@GetMapping("/*")
//	public String index(Model model) {
//		List<San> listSanActive = sanService.getAllSanActive();
//		List<Float> mocSao = new ArrayList<>();
//
//		for(San san: listSanActive) {
//			Float sao = 0f;
//			for(DanhGia dg: san.getListDanhGia()) {
//				sao += dg.getMocSao();
//			}
//			sao = sao / san.getListDanhGia().size();
//
//			mocSao.add(sao.isNaN() ? 0 : sao);
//		}
//
//		session.setAttribute("listSao", mocSao);
//		session.setAttribute("listSanActive", listSanActive);
//		session.setAttribute("rq", "/home.html");
//
//		return "index";
//	}
//
//	@GetMapping("/removeToast")
//	public String removeToast(Model model) {
//		if(session.getAttribute("showToastScript") != null)
//			session.removeAttribute("showToastScript");
//
//		return "index";
//	}
//
//	@GetMapping("news")
//	public String news() {
//		session.setAttribute("rq", "/other/news.html");
//		return "index";
//	}
//	@GetMapping("contact")
//	public String contact() {
//		session.setAttribute("rq", "/other/contact.html");
//		return "index";
//	}
//
//	@GetMapping("error-page")
//	public String errorPage() {
//		session.setAttribute("rq", "/other/error-page.html");
//		return "index";
//	}
}
