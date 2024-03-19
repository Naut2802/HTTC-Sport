package com.fpoly.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fpoly.model.User;
import com.fpoly.repository.UserRepo;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserRepo userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	User user = userRepository.findById(username).get();

    	if(user == null) {
    		throw new UsernameNotFoundException("Could not find user");
    	}
    	
    	return new CustomUserDetails(user);
    }
}