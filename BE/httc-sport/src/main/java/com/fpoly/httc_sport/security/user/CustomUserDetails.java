package com.fpoly.httc_sport.security.user;

import com.fpoly.httc_sport.entity.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;

@Data
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomUserDetails implements UserDetails {
	User user;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		StringJoiner stringJoiner = new StringJoiner(" ");
		
		if (!CollectionUtils.isEmpty(this.user.getRoleSet())) {
			this.user.getRoleSet().forEach(role -> {
				stringJoiner.add("ROLE_" + role.getRoleName());
//				if (!CollectionUtils.isEmpty(role.getPermissions())) {
//					role.getPermissions().forEach(permission -> stringJoiner.add(permission.getPermissionName()));
//				}
			});
		}
		
		List<String> roles = List.of(stringJoiner.toString().split(" "));
		return roles.stream().map(SimpleGrantedAuthority::new).toList();
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}
	
	@Override
	public String getUsername() {
		return user.getUsername();
	}
}
