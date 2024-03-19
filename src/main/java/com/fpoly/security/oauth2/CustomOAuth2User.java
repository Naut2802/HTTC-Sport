package com.fpoly.security.oauth2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.fpoly.model.Role;
import com.fpoly.model.User;

public class CustomOAuth2User implements OAuth2User {

    private User user;
    private Map<String, Object> attributes;
    
    public CustomOAuth2User(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }
    
    public User getUser() {
    	return user;
    }
    
	@Override
	public Map<String, Object> getAttributes() {
        return attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = user.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
         
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
         
        return authorities;
	}

	@Override
	public String getName() {
        return user.getUsername();
	}
 
    public String getEmail() {
        return user.getEmail();     
    }
}
