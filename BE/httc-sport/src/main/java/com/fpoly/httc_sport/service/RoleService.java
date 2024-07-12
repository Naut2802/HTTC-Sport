package com.fpoly.httc_sport.service;

import com.fpoly.httc_sport.dto.request.RoleRequest;
import com.fpoly.httc_sport.dto.response.RoleResponse;
import com.fpoly.httc_sport.exception.AppException;
import com.fpoly.httc_sport.exception.ErrorCode;
import com.fpoly.httc_sport.mapper.RoleMapper;
import com.fpoly.httc_sport.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@PreAuthorize("hasRole('ADMIN')")
public class RoleService {
	RoleMapper roleMapper;
	//	PermissionRepository permissionRepository;
	RoleRepository roleRepository;
	
	public RoleResponse createRole(RoleRequest request) {
		if (roleRepository.existsByRoleName(request.roleName()))
			throw new AppException(ErrorCode.EXISTED);
		
		var role = roleMapper.toRole(request);

//		var permissions = permissionRepository.findAllByPermissionNameIn(request.getPermissions());
//		role.setPermissions(new HashSet<>(permissions));
		
		roleRepository.save(role);
		return roleMapper.toRoleResponse(role);
	}
	
	public RoleResponse updateRole(long id, RoleRequest request) {
		var role = roleRepository.findById(id).orElseThrow(() ->
				new AppException(ErrorCode.ROLE_NOT_EXISTED));

//		List<Permission> permissions = permissionRepository.findAllByPermissionNameIn(request.getPermissions());
//		role.setPermissions(new HashSet<>(permissions));
		
		roleMapper.updateRole(role, request);
		return roleMapper.toRoleResponse(roleRepository.save(role));
	}
	
	public void deleteRole(long id) {
		if (!roleRepository.existsById(id))
			throw new AppException(ErrorCode.ROLE_NOT_EXISTED);
		
		roleRepository.deleteById(id);
	}
	
	public List<RoleResponse> getRoles() {
		return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
	}
}