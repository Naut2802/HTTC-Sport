package com.fpoly.httc_sport.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CloudinaryService {
	Cloudinary cloudinary;
	
	public Map uploadFile(MultipartFile file) throws IOException {
		Map options = ObjectUtils.asMap(
				"asset_folder", "httc-sport"
		);
		return cloudinary.uploader().upload(file.getBytes(), options);
	}
	
	public List<Map<String, Object>> getImages(Set<String> publicIds) throws Exception {
		ApiResponse response = cloudinary.api().resourcesByIds(publicIds, ObjectUtils.emptyMap());
		return (List<Map<String, Object>>) response.get("resources");
	}
	
	public void deleteImages(Set<String> publicIds) throws Exception {
		cloudinary.api().deleteResources(publicIds, ObjectUtils.emptyMap());
	}
}
