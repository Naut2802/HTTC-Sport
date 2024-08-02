package com.fpoly.httc_sport.service;

import com.fpoly.httc_sport.dto.request.RoleRequest;
import com.fpoly.httc_sport.dto.request.RoleUpdateRequest;
import com.fpoly.httc_sport.dto.response.RoleResponse;
import com.fpoly.httc_sport.exception.AppException;
import com.fpoly.httc_sport.exception.ErrorCode;
import com.fpoly.httc_sport.mapper.RoleMapper;
import com.fpoly.httc_sport.repository.RoleRepository;
import com.fpoly.httc_sport.utils.Enum;
import com.fpoly.httc_sport.utils.Enum.RoleEnum;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleService {
	RoleMapper roleMapper;
	RoleRepository roleRepository;
	
	public List<RoleResponse> getRoles() {
		return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
	}
}