package com.fpoly.httc_sport.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fpoly.httc_sport.repository.SanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.httc_sport.entity.DanhGia;
import com.fpoly.httc_sport.entity.San;

@Service
public class SanService {
	@Autowired
	private SanRepository sanRepository;
	
	public List<San> getAllSan() {
		return (List<San>) sanRepository.findAll();
	}
	
	public List<San> getAllSanActive() {
		return sanRepository.findAllByTrangThaiSanTrue();
	}
	
	public San getSan(Integer id) {
		return sanRepository.findById(id).orElse(null);
	}
	
	public void save(San san) {
		sanRepository.save(san);
	}
	
	public Boolean deleteSan(Integer id) {
		San san = sanRepository.findById(id).orElse(null);
		
		if(san != null) {
			san.setTrangThaiSan(false);
			sanRepository.save(san);
			return true;
		}
		return false;
	}
	public List<San> findbyKeyWords(String tenSan) {
		return sanRepository.findByTenSanContainingAndTrangThaiSanTrue(tenSan);
	}
	public List<San> findByLoaiSan(String loaiSan) {
		List<San> listSan = new ArrayList<>();
		if(loaiSan.contains(",")) {
			String[] listLoaiSan = loaiSan.split(",");
			List<San> _list = new ArrayList<>(); 
			for(String ls: listLoaiSan) {
				int _ls = Integer.parseInt(ls);
				_list = sanRepository.findByLoaiSanMaLoaiEqualsAndTrangThaiSanTrue(_ls);
				listSan.addAll(_list);
				_list.removeAll(_list);
			}
			return listSan;
		} else
			return sanRepository.findByLoaiSanMaLoaiEqualsAndTrangThaiSanTrue(Integer.parseInt(loaiSan));
	}
	public Set<San> findBySao(String stars, List<San> setSan) {
		List<San> listSan = setSan;
		Set<San> listResult = new HashSet<>();
		List<Float> mocSao = new ArrayList<>();
		
		for(San san: listSan) {
			Float sao = 0f;
			for(DanhGia dg: san.getListDanhGia()) {
				sao += dg.getMocSao();
			}
			sao = sao / san.getListDanhGia().size();
			
			mocSao.add(sao.isNaN() ? 0 : sao);
		}
		
		if(stars.contains(",")) {
			String[] listStar = stars.split(",");
			for(String star: listStar) {
				int _star = Integer.parseInt(star);
				for(int i = 0; i < listSan.size(); i++) {
					switch (_star) {
					case 1: {
						if(mocSao.get(i) <= _star+1) {
							listResult.add(listSan.get(i));
						}
					}
					case 2: {
						if(mocSao.get(i) > 2 && mocSao.get(i) < _star+1) {
							listResult.add(listSan.get(i));
						}
					}
					case 3: {
						if(mocSao.get(i) > 3 && mocSao.get(i) < _star+1) {
							listResult.add(listSan.get(i));
						}
					}
					case 4: {
						if(mocSao.get(i) > 4 && mocSao.get(i) < _star+1) {
							listResult.add(listSan.get(i));
						}
					}
					case 5: {
						if(mocSao.get(i) == _star) {
							listResult.add(listSan.get(i));
						}
					}
					}
				}
			}
		} else {
			int _star = Integer.parseInt(stars);
			for(int i = 0; i < listSan.size(); i++) {
				switch (_star) {
					case 1: {
						if(mocSao.get(i) <= _star+1) {
							listResult.add(listSan.get(i));
						}
					}
					case 2: {
						if(mocSao.get(i) > 2 && mocSao.get(i) < _star+1) {
							listResult.add(listSan.get(i));
						}
					}
					case 3: {
						if(mocSao.get(i) > 3 && mocSao.get(i) < _star+1) {
							listResult.add(listSan.get(i));
						}
					}
					case 4: {
						if(mocSao.get(i) > 4 && mocSao.get(i) < _star+1) {
							listResult.add(listSan.get(i));
						}
					}
					case 5: {
						if(mocSao.get(i) == _star) {
							listResult.add(listSan.get(i));
						}
					}
				}
			}
		}
		return listResult;
	}
	
	public Set<San> filter(String loaiSan, String stars) {
		Set<San> result = new HashSet<>();
		
		if(loaiSan.isEmpty() && stars.isEmpty()) {
			result = new HashSet<>(getAllSanActive());
		} else if(!loaiSan.isEmpty() && !stars.isEmpty()) {
			result = new HashSet<>();
			result.addAll(findByLoaiSan(loaiSan));
			result.addAll(new ArrayList<>(findBySao(stars, new ArrayList<>(result))));
			
			result = new HashSet<>(findBySao(stars, new ArrayList<>(result)));
		} else if(!loaiSan.isEmpty() && stars.isEmpty()) {
			result = new HashSet<>();
			result.addAll(findByLoaiSan(loaiSan));
		} else if(loaiSan.isEmpty() && !stars.isEmpty()) {
			result = new HashSet<>();
			result.addAll(findBySao(stars, getAllSanActive()));
		}
		return result;
	}
}
