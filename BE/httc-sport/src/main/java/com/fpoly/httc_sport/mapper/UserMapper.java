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
	
	@Mapping(target = "roles", ignore = true)
	@Mapping(target = "listThongTinDatSan", ignore = true)
	@Mapping(target = "listDanhGia", ignore = true)
	@Mapping(target = "listHoaDon", ignore = true)
	@Mapping(target = "vip", ignore = true)
	void updateUser(@MappingTarget User user, UserUpdateRequest request);
	
	@Mapping(target = "email", ignore = true)
	@Mapping(target = "roles", ignore = true)
	@Mapping(target = "listThongTinDatSan", ignore = true)
	@Mapping(target = "listDanhGia", ignore = true)
	@Mapping(target = "listHoaDon", ignore = true)
	@Mapping(target = "vip", ignore = true)
	void updateProfileUser(@MappingTarget User user, UserUpdateProfileRequest request);
	
	@Mapping(target = "roles", ignore = true)
	@Mapping(target = "listThongTinDatSan", ignore = true)
	@Mapping(target = "listDanhGia", ignore = true)
	@Mapping(target = "listHoaDon", ignore = true)
	@Mapping(target = "vip", ignore = true)
	void changePassword(@MappingTarget User user, UserUpdateRequest request);
}