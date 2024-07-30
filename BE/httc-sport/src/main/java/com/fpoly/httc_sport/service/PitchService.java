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
import com.fpoly.httc_sport.mapper.PitchMapper;
import com.fpoly.httc_sport.repository.AddressRepository;
import com.fpoly.httc_sport.repository.ImageRepository;
import com.fpoly.httc_sport.repository.PitchRepository;
import com.fpoly.httc_sport.repository.ReviewRepository;
import com.fpoly.httc_sport.repository.spec.PitchSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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
	ReviewRepository reviewRepository;
	ImageRepository imageRepository;
	PitchRepository pitchRepository;
	AddressRepository addressRepository;
	PitchMapper pitchMapper;
	ImageService imageService;
	
	public PitchResponse createPitch(PitchRequest request) throws IOException {
		if (addressRepository.existsByStreetAndDistrict(request.getStreet(), request.getDistrict()))
			throw new AppException(ErrorCode.PITCH_EXISTED);
		
		var pitch = pitchMapper.toPitch(request);
		var address = pitchMapper.toAddress(request);
		pitch.setAddress(address);
		address.setPitch(pitch);
		
		if (request.getImages() != null)
			pitch.setImages(new HashSet<>(imageService.saveWithPitch(request.getImages(), pitch)));
		
		return pitchMapper.toPitchResponse(pitchRepository.save(pitch));
	}
	
	@Transactional
	public PitchResponse updatePitch(int id, PitchRequest request) throws Exception {
		var pitch = pitchRepository.findById(id).orElseThrow(
				() -> new AppException(ErrorCode.PITCH_NOT_EXISTED));
		
		pitchMapper.updatePitch(pitch, request);
		
		if (request.getImages() != null) {
			if (pitch.getImages() != null) {
				imageService.deleteImages(pitch.getImages().stream().map(Image::getPublicId).toList());
				pitch.getImages().clear();
			}
			pitchRepository.save(pitch);
			
			List<Image> imageResponse = imageService.saveWithPitch(request.getImages(), pitch);
			pitch.getImages().addAll(imageResponse);
		}
		
		return pitchMapper.toPitchResponse(pitchRepository.save(pitch));
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
	
	public void deletePitch(int id) throws Exception {
		var pitch = pitchRepository.findById(id).orElseThrow(
				() -> new AppException(ErrorCode.PITCH_NOT_EXISTED));
		
		if (!pitch.getIsEnabled())
			return;
		
		pitch.setIsEnabled(false);
		if (pitch.getImages() != null) {
			imageService.deleteImages(pitch.getImages().stream().map(Image::getPublicId).toList());
			pitch.getImages().clear();
		}
		
		pitchRepository.save(pitch);
	}
	
	public PitchDetailsResponse getPitch(int id) {
		var pitch = pitchRepository.findById(id).orElseThrow(
				() -> new AppException(ErrorCode.PITCH_NOT_EXISTED));
		
		return pitchMapper.toPitchDetailsResponse(pitch);
	}
	
	public List<PitchResponse> getPitches(String rates, String district,
	                                      String city, String name,
	                                      String price, String type,
	                                      int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		int rate1 = 0;
		int rate2 = 0;
		int price1 = -1;
		int price2 = -1;
		
		if (rates != null) {
			try {
				var r = Arrays.stream(rates.split("-")).map(Integer::parseInt).toList();
				rate1 = r.getFirst();
				rate2 = r.getLast();
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		if (price != null) {
			try {
				var p = Arrays.stream(price.split("-")).map(Integer::parseInt).toList();
				price1 = p.getFirst();
				price2 = p.getLast();
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		
		Specification<Pitch> spec = Specification.where(PitchSpecification.hasEnabled())
				.and(rate1 >= 0 && rate2 > rate1 ? PitchSpecification.hasRateIn(rate1, rate2) : null)
				.and(district != null && city != null ? PitchSpecification.hasAddress(district, city) : null)
				.and(price1 >= 0 && price2 > price1 ? PitchSpecification.hasPriceBetween(price1, price2) : null)
				.and(name != null ? PitchSpecification.hasPitchNameContaining(name) : null)
				.and(type != null ? PitchSpecification.hasType(type) : null);
		
		return pitchRepository.findAll(spec, pageable).stream().map(pitchMapper::toPitchResponse).toList();
	}
	
//	public List<Pitch> findbyKeyWords(String tenSan) {
//		return pitchRepository.findByTenSanContainingAndTrangThaiSanTrue(tenSan);
//	}
//	public List<Pitch> findByLoaiSan(String loaiSan) {
//		List<Pitch> listPitch = new ArrayList<>();
//		if(loaiSan.contains(",")) {
//			String[] listLoaiSan = loaiSan.split(",");
//			List<Pitch> _list = new ArrayList<>();
//			for(String ls: listLoaiSan) {
//				int _ls = Integer.parseInt(ls);
//				_list = pitchRepository.findByLoaiSanMaLoaiEqualsAndTrangThaiSanTrue(_ls);
//				listPitch.addAll(_list);
//				_list.removeAll(_list);
//			}
//			return listPitch;
//		} else
//			return pitchRepository.findByLoaiSanMaLoaiEqualsAndTrangThaiSanTrue(Integer.parseInt(loaiSan));
//	}
//	public Set<Pitch> findBySao(String stars, List<Pitch> setPitch) {
//		List<Pitch> listPitch = setPitch;
//		Set<Pitch> listResult = new HashSet<>();
//		List<Float> mocSao = new ArrayList<>();
//
//		for(Pitch pitch : listPitch) {
//			Float sao = 0f;
//			for(Comment dg: pitch.getListComment()) {
//				sao += dg.getRate();
//			}
//			sao = sao / pitch.getListComment().size();
//
//			mocSao.add(sao.isNaN() ? 0 : sao);
//		}
//
//		if(stars.contains(",")) {
//			String[] listStar = stars.split(",");
//			for(String star: listStar) {
//				int _star = Integer.parseInt(star);
//				for(int i = 0; i < listPitch.size(); i++) {
//					switch (_star) {
//					case 1: {
//						if(mocSao.get(i) <= _star+1) {
//							listResult.add(listPitch.get(i));
//						}
//					}
//					case 2: {
//						if(mocSao.get(i) > 2 && mocSao.get(i) < _star+1) {
//							listResult.add(listPitch.get(i));
//						}
//					}
//					case 3: {
//						if(mocSao.get(i) > 3 && mocSao.get(i) < _star+1) {
//							listResult.add(listPitch.get(i));
//						}
//					}
//					case 4: {
//						if(mocSao.get(i) > 4 && mocSao.get(i) < _star+1) {
//							listResult.add(listPitch.get(i));
//						}
//					}
//					case 5: {
//						if(mocSao.get(i) == _star) {
//							listResult.add(listPitch.get(i));
//						}
//					}
//					}
//				}
//			}
//		} else {
//			int _star = Integer.parseInt(stars);
//			for(int i = 0; i < listPitch.size(); i++) {
//				switch (_star) {
//					case 1: {
//						if(mocSao.get(i) <= _star+1) {
//							listResult.add(listPitch.get(i));
//						}
//					}
//					case 2: {
//						if(mocSao.get(i) > 2 && mocSao.get(i) < _star+1) {
//							listResult.add(listPitch.get(i));
//						}
//					}
//					case 3: {
//						if(mocSao.get(i) > 3 && mocSao.get(i) < _star+1) {
//							listResult.add(listPitch.get(i));
//						}
//					}
//					case 4: {
//						if(mocSao.get(i) > 4 && mocSao.get(i) < _star+1) {
//							listResult.add(listPitch.get(i));
//						}
//					}
//					case 5: {
//						if(mocSao.get(i) == _star) {
//							listResult.add(listPitch.get(i));
//						}
//					}
//				}
//			}
//		}
//		return listResult;
//	}
//
//	public Set<Pitch> filter(String loaiSan, String stars) {
//		Set<Pitch> result = new HashSet<>();
//
//		if(loaiSan.isEmpty() && stars.isEmpty()) {
//			result = new HashSet<>(getAllSanActive());
//		} else if(!loaiSan.isEmpty() && !stars.isEmpty()) {
//			result = new HashSet<>();
//			result.addAll(findByLoaiSan(loaiSan));
//			result.addAll(new ArrayList<>(findBySao(stars, new ArrayList<>(result))));
//
//			result = new HashSet<>(findBySao(stars, new ArrayList<>(result)));
//		} else if(!loaiSan.isEmpty() && stars.isEmpty()) {
//			result = new HashSet<>();
//			result.addAll(findByLoaiSan(loaiSan));
//		} else if(loaiSan.isEmpty() && !stars.isEmpty()) {
//			result = new HashSet<>();
//			result.addAll(findBySao(stars, getAllSanActive()));
//		}
//		return result;
//	}
}
