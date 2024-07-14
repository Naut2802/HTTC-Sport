package com.fpoly.httc_sport.config;

import com.cloudinary.Cloudinary;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CloudinaryConfig {
	@Value("${cloudinary.cloud-name}")
	String CLOUD_NAME;
	
	@Value("${cloudinary.api-key}")
	String API_KEY;
	
	@Value("${cloudinary.api-secret}")
	String API_SECRET;
	
	@Bean
	public Cloudinary cloudinary() {
		Map<String, String> config = new HashMap<>();
		config.put("cloud_name", CLOUD_NAME);
		config.put("api_key", API_KEY);
		config.put("api_secret", API_SECRET);
		return new Cloudinary(config);
	}
}
