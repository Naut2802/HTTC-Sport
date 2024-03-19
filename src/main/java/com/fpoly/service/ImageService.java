package com.fpoly.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.model.Image;
import com.fpoly.repository.ImageRepo;

@Service
public class ImageService {
	@Autowired
	private ImageRepo imageRepository;
	
	public void save(Image image) {
		imageRepository.save(image);
	}
	
	public void deleteAll(int maSan) {
		List<Image> listImages = imageRepository.findBySanMaSan(maSan);
		
		imageRepository.deleteAll(listImages);
	}
}
