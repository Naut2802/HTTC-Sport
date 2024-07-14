package com.fpoly.httc_sport.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fpoly.httc_sport.dto.request.ImageRequest;
import com.fpoly.httc_sport.dto.response.ImageResponse;
import com.fpoly.httc_sport.mapper.ImageMapper;
import com.fpoly.httc_sport.repository.ImageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageService {
	ImageRepository imageRepository;
	ImageMapper imageMapper;
	CloudinaryService cloudinaryService;
	public List<ImageResponse> save(List<MultipartFile> images) throws IOException {
		List<ImageResponse> responses = new ArrayList<>();
		images.forEach(image -> {
			try {
				var uploadResult = cloudinaryService.uploadFile(image);
				ImageResponse imageResponse = ImageResponse.builder()
						.publicId(uploadResult.get("public_id").toString())
						.url(uploadResult.get("url").toString())
						.build();
				
				responses.add(imageResponse);
				
				imageRepository.save(imageMapper.toImage(imageResponse));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
		
		return responses;
	}
	
	public List<ImageResponse> getImages(ImageRequest request) throws Exception {
		return imageRepository.findAllByPublicIdIn(request.getPublicIds()).stream().map(imageMapper::toImageResponse).toList();
	}
	
	@Transactional
	public void deleteImages(ImageRequest request) throws Exception {
		request.getPublicIds().forEach(image -> {
			if (imageRepository.existsByPublicId(image))
				imageRepository.deleteByPublicId(image);
		});
		cloudinaryService.deleteImages(request.getPublicIds());
	}
}
