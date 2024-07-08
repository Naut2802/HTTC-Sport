package com.fpoly.httc_sport.service;

import java.util.List;

import com.fpoly.httc_sport.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.httc_sport.entity.Image;

@Service
public class ImageService {
	@Autowired
	private ImageRepository imageRepository;
	
	public void save(Image image) {
		imageRepository.save(image);
	}
	
	public void deleteAll(int maSan) {
		List<Image> listImages = imageRepository.findBySanMaSan(maSan);
		
		imageRepository.deleteAll(listImages);
	}
}
