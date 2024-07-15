package com.fpoly.httc_sport.controller;

import com.fpoly.httc_sport.dto.request.RoleRequest;
import com.fpoly.httc_sport.dto.response.ApiResponse;
import com.fpoly.httc_sport.dto.response.RoleResponse;
import com.fpoly.httc_sport.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/role")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@PreAuthorize("hasRole('ADMIN')")
public class RoleController {
	RoleService roleService;
	
	@PostMapping
	public ApiResponse<RoleResponse> createRole(@RequestBody RoleRequest request) {
		return ApiResponse.<RoleResponse>builder()
				.result(roleService.createRole(request))
				.build();
	}
	
	@PutMapping("/{id}")
	public ApiResponse<RoleResponse> updateRole(@PathVariable String id, @RequestBody RoleRequest request) {
		return ApiResponse.<RoleResponse>builder()
				.result(roleService.updateRole(id, request))
				.build();
	}
	
	@DeleteMapping("/{id}")
	public ApiResponse<String> deleteRole(@PathVariable String id) {
		roleService.deleteRole(id);
		return ApiResponse.<String>builder()
				.result("Role has been deleted.")
				.build();
	}
	
	@GetMapping
	public ApiResponse<List<RoleResponse>> getRoles() {
		return ApiResponse.<List<RoleResponse>>builder()
				.result(roleService.getRoles())
				.build();
	}
}
