package com.fpoly.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fpoly.model.HoaDon;
import com.fpoly.model.Role;
import com.fpoly.model.ThongTinDatSan;
import com.fpoly.model.User;
import com.fpoly.model.Vip;
import com.fpoly.repository.HoaDonRepo;
import com.fpoly.repository.ThongTinDatRepository;
import com.fpoly.repository.UserRepo;
import com.fpoly.repository.VipRepo;

@Service
public class UserService {
	@Autowired
	private UserRepo userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private HoaDonRepo hoadonRepository;
	@Autowired
	private VipRepo VipRepository;
	@Autowired
	private ThongTinDatRepository thongtinRepository;
	private int time = 0; 
	
	public List<User> getAllUsers() {
		List<User> users = (List<User>) userRepository.findUserByRolesRoleNameLike("USER");
		
		return users;
	}
	
	public Boolean delete(String username) {
		User _userFind = userRepository.findById(username).orElse(null);
		if(_userFind != null) {
			_userFind.setStatus(false);
			userRepository.save(_userFind);
			return true;
		}
		
		return false;
	}
	
	public Boolean register(User user) {
		Boolean _userFindByUsername = userRepository.existsById(user.getUsername());
		Boolean _userFindByEmail = userRepository.existsByEmail(user.getEmail());
		
		if(_userFindByEmail || _userFindByUsername) 
			return false;
		else {
			Set<Role> roles = new HashSet<>();
			roles.add(new Role(2, "USER", null));
			User newUser = new User(user.getUsername(), user.getEmail(), passwordEncoder.encode(user.getPassword()), roles);
			userRepository.save(newUser);
			return true;
		}
	}
	
	public void save(User user) {
		userRepository.save(user);
	}
	
	public User edit(String email) {
		return userRepository.findUserByEmail(email) != null ? userRepository.findUserByEmail(email) : null;
	}
	
	public User edit2(String username) {
		return userRepository.findById(username).orElse(null);
	}
	
	public List<Vip> findAll(){
		return VipRepository.findAll();
	}
	
	public List<HoaDon> findbyId(String username){
		return (List<HoaDon>) hoadonRepository.findByUserUsername(username);
	}
	
	public List<ThongTinDatSan> findByUser(String username){
		return (List<ThongTinDatSan>) thongtinRepository.findByUserUsername(username);
	}
//	@Scheduled(fixedDelay = 1000, initialDelay = 1000)
//	public void checkTimeOtp() {
//		time++;
//		if(time == 20) {
//			
//		}
//	}
}
