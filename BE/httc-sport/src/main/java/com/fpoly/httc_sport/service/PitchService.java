package com.fpoly.httc_sport.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fpoly.httc_sport.repository.PitchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.httc_sport.entity.Comment;
import com.fpoly.httc_sport.entity.Pitch;

@Service
public class PitchService {
//	@Autowired
//	private PitchRepository pitchRepository;
//
//	public List<Pitch> getAllSan() {
//		return (List<Pitch>) pitchRepository.findAll();
//	}
//
//	public List<Pitch> getAllSanActive() {
//		return pitchRepository.findAllByIsEnabledTrue();
//	}
//
//	public Pitch getSan(Integer id) {
//		return pitchRepository.findById(id).orElse(null);
//	}
//
//	public void save(Pitch pitch) {
//		pitchRepository.save(pitch);
//	}
//
//	public Boolean deleteSan(Integer id) {
//		Pitch pitch = pitchRepository.findById(id).orElse(null);
//
//		if(pitch != null) {
//			pitch.setIsEnabled(false);
//			pitchRepository.save(pitch);
//			return true;
//		}
//		return false;
//	}
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