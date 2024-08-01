package com.fpoly.httc_sport.controller;

import com.fpoly.httc_sport.dto.request.PitchRequest;
import com.fpoly.httc_sport.dto.response.ApiResponse;
import com.fpoly.httc_sport.dto.response.PitchDetailsResponse;
import com.fpoly.httc_sport.dto.response.PitchResponse;
import com.fpoly.httc_sport.service.PitchService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/pitch")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PitchController {
	PitchService pitchService;
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<PitchResponse> createPitch(@Valid @ModelAttribute PitchRequest request) throws IOException {
		return ApiResponse.<PitchResponse>builder()
				.message("Thêm sân thành công")
				.result(pitchService.createPitch(request))
				.build();
	}
	
	@PutMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<PitchResponse> updatePitch(@PathVariable int id, @Valid @ModelAttribute PitchRequest request) throws Exception {
		return ApiResponse.<PitchResponse>builder()
				.message("Chỉnh sửa sân thành công")
				.result(pitchService.updatePitch(id, request))
				.build();
	}
	
	@DeleteMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<PitchResponse> deletePitch(@PathVariable int id) throws Exception {
		pitchService.deletePitch(id);
		return ApiResponse.<PitchResponse>builder()
				.message("Xóa sân thành công")
				.build();
	}
	
	@PatchMapping("{id}/{publicId}")
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<PitchResponse> deleteImageFromPitch(@PathVariable int id, @PathVariable String publicId) throws Exception {
		return ApiResponse.<PitchResponse>builder()
				.message("Xóa ảnh thành công")
				.result(pitchService.deleteImageFromPitch(id, publicId))
				.build();
	}
	
	@GetMapping("{id}")
	ApiResponse<PitchDetailsResponse> getPitch(@PathVariable int id) {
		return ApiResponse.<PitchDetailsResponse>builder()
				.result(pitchService.getPitch(id))
				.build();
	}
	
	@GetMapping
	ApiResponse<List<PitchResponse>> getPitches(
			@RequestParam(value = "rates", required = false) String rates,
			@RequestParam(value = "district", required = false) String district,
			@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "price", required = false) String price,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		return ApiResponse.<List<PitchResponse>>builder()
				.result(pitchService.getPitches(rates, district, city, name, price, type, page, size))
				.build();
	}
	
	@GetMapping("admin")
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<List<PitchResponse>> getPitchesByAdmin(
			@RequestParam(value = "rates", required = false) String rates,
			@RequestParam(value = "district", required = false) String district,
			@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "price", required = false) String price,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		return ApiResponse.<List<PitchResponse>>builder()
				.result(pitchService.getPitchesByAdmin(rates, district, city, name, price, type, page, size))
				.build();
	}
	
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
