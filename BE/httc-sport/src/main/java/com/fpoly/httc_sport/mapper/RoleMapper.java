package com.fpoly.httc_sport.mapper;

import com.fpoly.httc_sport.dto.response.RoleResponse;
import com.fpoly.httc_sport.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
	RoleResponse toRoleResponse(Role role);
}