package com.fpoly.security.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.fpoly.model.User;
import com.fpoly.repository.UserRepo;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	@Autowired
	UserRepo userRepo;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
	    OAuth2User oAuth2User = super.loadUser(userRequest);
    	User user = userRepo.findUserByEmail(oAuth2User.getAttribute("email"));
    	if(user == null) {
    		throw new UsernameNotFoundException("Could not find user");
    	}
    	
    	return new CustomOAuth2User(user, oAuth2User.getAttributes());
	}
	
}
