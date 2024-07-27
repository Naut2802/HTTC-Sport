package com.fpoly.httc_sport.controller;

import com.fpoly.httc_sport.dto.request.ImageRequest;
import com.fpoly.httc_sport.dto.response.ApiResponse;
import com.fpoly.httc_sport.dto.response.ImageResponse;
import com.fpoly.httc_sport.service.ImageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/image")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@PreAuthorize("hasRole('ADMIN')")
public class ImageController {
	ImageService imageService;
	
	@PostMapping
	ApiResponse<List<ImageResponse>> uploadImage(@RequestParam("image") List<MultipartFile> images) throws IOException {
		return ApiResponse.<List<ImageResponse>>builder()
				.result(imageService.save(images))
				.build();
	}
	
	@GetMapping
	ApiResponse<List<ImageResponse>> getImages(@RequestBody ImageRequest request) throws Exception {
		return ApiResponse.<List<ImageResponse>>builder()
				.result(imageService.getImages(request))
				.build();
	}
	
	@DeleteMapping
	ApiResponse<?> deleteImages(@RequestBody ImageRequest request) throws Exception {
		imageService.deleteImagesFromApi(request);
		return ApiResponse.builder()
				.message("Xóa hình ảnh thành công")
				.build();
	}
}
