package com.fpoly.httc_sport.service;

import com.cloudinary.Cloudinary;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CloudinaryService {
	Cloudinary cloudinary;
	
	public String uploadFile(MultipartFile file) throws IOException {
		return cloudinary.uploader().upload(
				file.getBytes(),
				Map.of("public_id", UUID.randomUUID())
		).toString();
	}
}
