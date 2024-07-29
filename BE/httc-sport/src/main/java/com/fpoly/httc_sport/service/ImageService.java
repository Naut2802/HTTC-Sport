package com.fpoly.httc_sport.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.fpoly.httc_sport.dto.request.ImageRequest;
import com.fpoly.httc_sport.dto.response.ImageResponse;
import com.fpoly.httc_sport.entity.Image;
import com.fpoly.httc_sport.entity.Pitch;
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
	
	public List<Image> saveWithPitch(List<MultipartFile> images, Pitch pitch) throws IOException {
		List<Image> responses = new ArrayList<>();
		images.forEach(image -> {
			try {
				var uploadResult = cloudinaryService.uploadFile(image);
				Image imageResponse = Image.builder()
						.publicId(uploadResult.get("public_id").toString())
						.url(uploadResult.get("url").toString())
						.pitch(pitch)
						.build();
				
				responses.add(imageResponse);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
		
		return responses;
	}
	
	public List<ImageResponse> getImages(ImageRequest request) throws Exception {
		return imageRepository.findByPublicIdIn(request.getPublicIds()).stream().map(imageMapper::toImageResponse).toList();
	}
	
	public void deleteImages(List<String> publicIds) throws Exception {
		cloudinaryService.deleteImages(new HashSet<>(publicIds));
	}
}
