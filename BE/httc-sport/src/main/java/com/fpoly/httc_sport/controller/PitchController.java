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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/pitch")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Pitch Controller")
@Slf4j
public class PitchController {
	PitchService pitchService;
	
	@Operation(summary = "Api add new pitch", description = "Admin use this api to add a pitch")
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<PitchResponse> createPitch(@Valid @ModelAttribute PitchRequest request) throws IOException {
		log.info("[Pitch Controller - Create pitch api] Admin adding a new pitch");
		var response = pitchService.createPitch(request);
		log.info("[Pitch Controller - Create pitch api] New pitch added");
		return ApiResponse.<PitchResponse>builder()
				.message("Thêm sân thành công")
				.result(response)
				.build();
	}
	
	@Operation(summary = "Api update pitch", description = "Admin use this api to update a pitch")
	@PutMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<PitchDetailsResponse> updatePitch(@PathVariable int id, @Valid @ModelAttribute PitchRequest request) throws Exception {
		log.info("[Pitch Controller - Update pitch api] Admin updating a pitch with pitch-id: {}", id);
		var response = pitchService.updatePitch(id, request);
		log.info("[Pitch Controller - Update pitch api] Updated");
		return ApiResponse.<PitchDetailsResponse>builder()
				.message("Chỉnh sửa sân thành công")
				.result(response)
				.build();
	}
	
	@Operation(summary = "Api deactivate pitch", description = "Admin use this api to deactivate a pitch")
	@DeleteMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<PitchResponse> deletePitch(@PathVariable int id) throws Exception {
		log.info("[Pitch Controller - Deactivate pitch api] Admin deactivate a pitch with id: {}", id);
		var response = pitchService.deletePitch(id);
		log.info("[Pitch Controller - Deactivate pitch api] {}", response);
		return ApiResponse.<PitchResponse>builder()
				.message(response)
				.build();
	}
	
	@Operation(summary = "Api active pitch", description = "Admin use this api to active a pitch")
	@PatchMapping("/active/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<PitchResponse> activePitch(@PathVariable int id) throws Exception {
		log.info("[Pitch Controller - Active pitch api] Admin active a pitch with id: {}", id);
		var response = pitchService.activePitch(id);
		log.info("[Pitch Controller - Active pitch api] {}", response);
		return ApiResponse.<PitchResponse>builder()
				.message(response)
				.build();
	}
	
	@Operation(summary = "Api delete image", description = "Admin use this api delete a image from a pitch")
	@PatchMapping("{id}/{publicId}")
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<PitchResponse> deleteImageFromPitch(@PathVariable int id, @PathVariable String publicId) throws Exception {
		log.info("[Pitch Controller - Delete a image from pitch] Admin deleting a image from a pitch with pitch-id: {}", id);
		var response = pitchService.deleteImageFromPitch(id, publicId);
		log.info("[Pitch Controller - Delete a image from pitch] Image deleted");
		return ApiResponse.<PitchResponse>builder()
				.message("Xóa ảnh thành công")
				.result(response)
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
}
