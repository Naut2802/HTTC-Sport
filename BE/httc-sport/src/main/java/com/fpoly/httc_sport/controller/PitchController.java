package com.fpoly.httc_sport.controller;

import com.fpoly.httc_sport.dto.request.PitchRequest;
import com.fpoly.httc_sport.dto.response.ApiResponse;
import com.fpoly.httc_sport.dto.response.PitchDetailsResponse;
import com.fpoly.httc_sport.dto.response.PitchResponse;
import com.fpoly.httc_sport.service.PitchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Pitch Controller")
public class PitchController {
	PitchService pitchService;
	
	@Operation(summary = "Api add new pitch", description = "Admin use this api to add a pitch")
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<PitchResponse> createPitch(@Valid @ModelAttribute PitchRequest request) throws IOException {
		return ApiResponse.<PitchResponse>builder()
				.message("Thêm sân thành công")
				.result(pitchService.createPitch(request))
				.build();
	}
	
	@Operation(summary = "Api update pitch", description = "Admin use this api to update a pitch")
	@PutMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<PitchDetailsResponse> updatePitch(@PathVariable int id, @Valid @ModelAttribute PitchRequest request) throws Exception {
		return ApiResponse.<PitchDetailsResponse>builder()
				.message("Chỉnh sửa sân thành công")
				.result(pitchService.updatePitch(id, request))
				.build();
	}
	
	@Operation(summary = "Api deactivate pitch", description = "Admin use this api to deactivate a pitch")
	@DeleteMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<PitchResponse> deletePitch(@PathVariable int id) throws Exception {
		return ApiResponse.<PitchResponse>builder()
				.message(pitchService.deletePitch(id))
				.build();
	}
	
	@Operation(summary = "Api active pitch", description = "Admin use this api to active a pitch")
	@PatchMapping("/active/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<PitchResponse> activePitch(@PathVariable int id) throws Exception {
		return ApiResponse.<PitchResponse>builder()
				.message(pitchService.activePitch(id))
				.build();
	}
	
	@Operation(summary = "Api delete image", description = "Admin use this api delete a image from a pitch")
	@PatchMapping("{id}/{publicId}")
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<PitchResponse> deleteImageFromPitch(@PathVariable int id, @PathVariable String publicId) throws Exception {
		return ApiResponse.<PitchResponse>builder()
				.message("Xóa ảnh thành công")
				.result(pitchService.deleteImageFromPitch(id, publicId))
				.build();
	}
	
	@Operation(summary = "Api get a pitch", description = "Api use to get a pitch with pitch-id")
	@GetMapping("{id}")
	ApiResponse<PitchDetailsResponse> getPitch(@PathVariable int id) {
		return ApiResponse.<PitchDetailsResponse>builder()
				.result(pitchService.getPitch(id))
				.build();
	}
	
	@Operation(summary = "Api get all active pitches")
	@GetMapping
	ApiResponse<List<PitchResponse>> getPitches(
			@RequestParam(required = false) String rates,
			@RequestParam(required = false) String district,
			@RequestParam(required = false) String city,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String price,
			@RequestParam(required = false) String type,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		return ApiResponse.<List<PitchResponse>>builder()
				.result(pitchService.getPitches(rates, district, city, name, price, type, page, size))
				.build();
	}
	
	@Operation(summary = "Api get all pitches", description = "Admin use this api to get all pitches")
	@GetMapping("admin")
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<List<PitchResponse>> getPitchesByAdmin(
			@RequestParam(required = false) String rates,
			@RequestParam(required = false) String district,
			@RequestParam(required = false) String city,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String price,
			@RequestParam(required = false) String type,
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
