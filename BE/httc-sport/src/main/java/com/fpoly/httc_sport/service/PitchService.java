package com.fpoly.httc_sport.service;

import java.io.IOException;
import java.util.*;

import com.fpoly.httc_sport.dto.request.PitchRequest;
import com.fpoly.httc_sport.dto.response.PitchDetailsResponse;
import com.fpoly.httc_sport.dto.response.PitchResponse;
import com.fpoly.httc_sport.entity.Image;
import com.fpoly.httc_sport.entity.Pitch;
import com.fpoly.httc_sport.exception.AppException;
import com.fpoly.httc_sport.exception.ErrorCode;
import com.fpoly.httc_sport.mapper.ImageMapper;
import com.fpoly.httc_sport.mapper.PitchMapper;
import com.fpoly.httc_sport.repository.AddressRepository;
import com.fpoly.httc_sport.repository.ImageRepository;
import com.fpoly.httc_sport.repository.PitchRepository;
import com.fpoly.httc_sport.repository.specification.PitchSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PitchService {
	ImageRepository imageRepository;
	PitchRepository pitchRepository;
	AddressRepository addressRepository;
	PitchMapper pitchMapper;
	ImageMapper imageMapper;
	ImageService imageService;
	
	public PitchResponse createPitch(PitchRequest request) throws IOException {
		if (addressRepository.existsByStreetAndDistrict(request.getStreet(), request.getDistrict()))
			throw new AppException(ErrorCode.PITCH_EXISTED);
		
		if (request.getImages() == null)
			throw new AppException(ErrorCode.PITCH_IMAGES_NULL);
		
		if (request.getImages().size() < 5 || request.getImages().size() > 10)
			throw new AppException(ErrorCode.PITCH_IMAGES_SIZE);
		
		var pitch = pitchMapper.toPitch(request);
		var address = pitchMapper.toAddress(request);
		pitch.setAddress(address);
		address.setPitch(pitch);
		
		if (request.getImages() != null)
			pitch.setImages(imageService.saveWithPitch(request.getImages(), pitch));
		
		return pitchMapper.toPitchResponse(pitchRepository.save(pitch));
	}
	
	@Transactional
	public PitchDetailsResponse updatePitch(int id, PitchRequest request) throws Exception {
		var pitch = pitchRepository.findById(id).orElseThrow(
				() -> new AppException(ErrorCode.PITCH_NOT_EXISTED));
		
		pitchMapper.updatePitch(pitch, request);
		
		if (request.getImages() != null) {
			if (!pitch.getImages().isEmpty()) {
				imageService.deleteImages(pitch.getImages().stream().map(Image::getPublicId).toList());
				pitch.getImages().clear();
				pitchRepository.save(pitch);
			}
			
			List<Image> imageResponse = imageService.saveWithPitch(request.getImages(), pitch);
			pitch.getImages().addAll(imageResponse);
		}
		
		return pitchMapper.toPitchDetailsResponse(pitchRepository.save(pitch));
	}
	
	public PitchResponse deleteImageFromPitch(int id, String publicId) throws Exception {
		var pitch = pitchRepository.findById(id).orElseThrow(
				() -> new AppException(ErrorCode.PITCH_NOT_EXISTED));
		
		Image imageToRemove = pitch.getImages().stream()
				.filter(image -> image.getPublicId().equals(publicId))
				.findFirst()
				.orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTED));
		
		pitch.getImages().remove(imageToRemove);
		imageRepository.delete(imageToRemove);
		imageService.deleteImages(List.of(imageToRemove.getPublicId()));
		return pitchMapper.toPitchResponse(pitchRepository.save(pitch));
	}
	
	public String deletePitch(int id) throws Exception {
		var pitch = pitchRepository.findById(id).orElseThrow(
				() -> new AppException(ErrorCode.PITCH_NOT_EXISTED));
		
		if (!pitch.getIsEnabled())
			return "Sân này đã ngừng hoạt động";
		
		pitch.setIsEnabled(false);
		pitchRepository.save(pitch);
		
		return "Ngưng hoạt động sân thành công";
	}
	
	public String activePitch(int id) throws Exception {
		var pitch = pitchRepository.findById(id).orElseThrow(
				() -> new AppException(ErrorCode.PITCH_NOT_EXISTED));
		
		if (pitch.getIsEnabled())
			return "Sân này đang hoạt động";
		
		pitch.setIsEnabled(true);
		pitchRepository.save(pitch);
		
		return "Kích hoạt sân thành công";
	}
	
	public PitchDetailsResponse getPitch(int id) {
		var pitch = pitchRepository.findById(id).orElseThrow(
				() -> new AppException(ErrorCode.PITCH_NOT_EXISTED));
		
		return pitchMapper.toPitchDetailsResponse(pitch);
	}
	
	public List<PitchResponse> getPitches(String rates, String district, String city,
	                                      String name, String price, String type, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		int rate1 = 0;
		int rate2 = 0;
		int price1 = -1;
		int price2 = -1;
		
		if (rates != null && !rates.isBlank()) {
			try {
				var r = Arrays.stream(rates.replace(" ", "").split("-")).map(Integer::parseInt).toList();
				rate1 = r.getFirst();
				rate2 = r.getLast();
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		if (price != null && !price.isBlank()) {
			try {
				var p = Arrays.stream(price.replace(" ", "").split("-")).map(Integer::parseInt).toList();
				price1 = p.getFirst();
				price2 = p.getLast();
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		
		Specification<Pitch> spec = PitchSpecification.hasEnabled()
				.and(rate1 >= 0 && rate2 > rate1 ? PitchSpecification.hasRateIn(rate1, rate2) : null)
				.and((district != null && city != null) && (!district.isBlank() && !city.isBlank()) ? PitchSpecification.hasAddress(district, city) : null)
				.and(price1 >= 0 && price2 > price1 ? PitchSpecification.hasPriceBetween(price1, price2) : null)
				.and(name != null && !name.isBlank() ? PitchSpecification.hasPitchNameContaining(name) : null);
		
		Page<Pitch> pitches = pitchRepository.findAll(spec, pageable);
		List<Optional<Image>> images = new ArrayList<>(pitches
				.stream().map(pitch -> pitch.getImages()
						.stream().findFirst()).toList());
		
		var responses = pitches.stream().filter(pitch -> {
			if (type != null) {
					if (type.equals("7"))
						return pitch.getTotal() >= 3;
					else if (type.equals("11")) {
						return pitch.getTotal() >= 9;
					} else
						return true;
			}
			return true;
		}).map(pitchMapper::toPitchResponse).toList();
		
		int index = 0;
		for(Optional<Image> image: images) {
			if (image.isPresent())
				responses.get(index).setImage(imageMapper.toImageResponse(image.get()));
			
			index++;
		}
		
		return responses;
	}
	
	public List<PitchResponse> getPitchesByAdmin(String rates, String district, String city,
	                                             String name, String price, String type, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		int rate1 = 0;
		int rate2 = 0;
		int price1 = -1;
		int price2 = -1;
		
		if (rates != null && !rates.isBlank()) {
			try {
				var r = Arrays.stream(rates.replace(" ", "").split("-")).map(Integer::parseInt).toList();
				rate1 = r.getFirst();
				rate2 = r.getLast();
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		if (price != null && !price.isBlank()) {
			try {
				var p = Arrays.stream(price.replace(" ", "").split("-")).map(Integer::parseInt).toList();
				price1 = p.getFirst();
				price2 = p.getLast();
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		
		Specification<Pitch> spec = (rate1 > 0 && rate2 > rate1 ? PitchSpecification.hasRateIn(rate1, rate2) : PitchSpecification.hasRateIn(0, 5))
				.and((district != null && city != null) && (!district.isBlank() && !city.isBlank()) ? PitchSpecification.hasAddress(district, city) : null)
				.and(price1 >= 0 && price2 > price1 ? PitchSpecification.hasPriceBetween(price1, price2) : null)
				.and(name != null && !name.isBlank() ? PitchSpecification.hasPitchNameContaining(name) : null);
		
		Page<Pitch> pitches = pitchRepository.findAll(spec, pageable);
		List<Optional<Image>> images = new ArrayList<>(pitches
				.stream().map(pitch -> pitch.getImages()
						.stream().findFirst()).toList());
		
		var responses = pitches.stream().filter(pitch -> {
			if (type != null) {
				if (type.equals("7"))
					return pitch.getTotal() >= 3;
				else if (type.equals("11")) {
					return pitch.getTotal() >= 9;
				} else
					return true;
			}
			return true;
		}).map(pitchMapper::toPitchResponse).toList();
		
		int index = 0;
		for(Optional<Image> image: images) {
			if (image.isPresent())
				responses.get(index).setImage(imageMapper.toImageResponse(image.get()));
			
			index++;
		}
		
		return responses;
	}
}
