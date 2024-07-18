package com.fpoly.httc_sport.mapper;

import com.fpoly.httc_sport.dto.request.RegisterRequest;
import com.fpoly.httc_sport.dto.request.UserUpdateProfileRequest;
import com.fpoly.httc_sport.dto.request.UserUpdateRequest;
import com.fpoly.httc_sport.dto.response.UserResponse;
import com.fpoly.httc_sport.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
	User toUser(RegisterRequest request);
	
	UserResponse toUserResponse(User user);
	
	void updateUser(@MappingTarget User user, UserUpdateRequest request);
	
	void updateProfileUser(@MappingTarget User user, UserUpdateProfileRequest request);
}