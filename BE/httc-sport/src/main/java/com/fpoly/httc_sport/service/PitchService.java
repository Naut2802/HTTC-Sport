package com.fpoly.httc_sport.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fpoly.httc_sport.dto.request.PitchRequest;
import com.fpoly.httc_sport.dto.response.ImageResponse;
import com.fpoly.httc_sport.dto.response.PitchDetailsResponse;
import com.fpoly.httc_sport.dto.response.PitchResponse;
import com.fpoly.httc_sport.entity.Address;
import com.fpoly.httc_sport.entity.Image;
import com.fpoly.httc_sport.exception.AppException;
import com.fpoly.httc_sport.exception.ErrorCode;
import com.fpoly.httc_sport.mapper.ImageMapper;
import com.fpoly.httc_sport.mapper.PitchMapper;
import com.fpoly.httc_sport.repository.AddressRepository;
import com.fpoly.httc_sport.repository.PitchRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.httc_sport.entity.Comment;
import com.fpoly.httc_sport.entity.Pitch;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PitchService {
	PitchRepository pitchRepository;
	AddressRepository addressRepository;
	PitchMapper pitchMapper;
	ImageService imageService;
	
	public PitchResponse createPitch(PitchRequest request) throws IOException {
		if (addressRepository.existsByStreetAndDistrict(request.getStreet(), request.getDistrict()))
			throw new AppException(ErrorCode.PITCH_EXISTED);
		
		var pitch = pitchMapper.toPitch(request);
		pitch.setRemaining(request.getTotal());
		pitch.setImages(new HashSet<>(imageService.saveWithPitch(request.getImages(), pitch)));
		pitch.setAddress(pitchMapper.toAddress(request));
		
		return pitchMapper.toPitchResponse(pitchRepository.save(pitch));
	}
	
	public PitchResponse updatePitch(int id, PitchRequest request) throws IOException {
		var pitch = pitchRepository.findById(id).orElseThrow(
				() -> new AppException(ErrorCode.PITCH_NOT_EXISTED));
		
		pitchMapper.updatePitch(pitch, request);
		
		if (!request.getImages().isEmpty()) {
			List<Image> images = List.copyOf(pitch.getImages());
			
			var imageResponse = imageService.saveWithPitch(request.getImages(), pitch);
			imageResponse.addAll(images);
			
			pitch.setImages(new HashSet<>(images));
		}
		
		return pitchMapper.toPitchResponse(pitchRepository.save(pitch));
	}
	
	public void deletePitch(int id) {
		var pitch = pitchRepository.findById(id).orElseThrow(
				() -> new AppException(ErrorCode.PITCH_NOT_EXISTED));
		
		if (pitch.getIsEnabled())
			return;
		
		pitch.setIsEnabled(false);
		
		pitchRepository.save(pitch);
	}
	
	public PitchDetailsResponse getPitch(int id) {
		var pitch = pitchRepository.findById(id).orElseThrow(
				() -> new AppException(ErrorCode.PITCH_NOT_EXISTED));
		
		return pitchMapper.toPitchDetailsResponse(pitch);
	}
	
	public List<PitchResponse> getPitches() {
		return pitchRepository.findAllByIsEnabledTrue().stream().map(pitchMapper::toPitchResponse).toList();
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
