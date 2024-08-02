package com.fpoly.httc_sport.mapper;

import com.fpoly.httc_sport.dto.request.RoleRequest;
import com.fpoly.httc_sport.dto.request.RoleUpdateRequest;
import com.fpoly.httc_sport.dto.response.RoleResponse;
import com.fpoly.httc_sport.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {
	RoleResponse toRoleResponse(Role role);
}