package com.fpoly.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.repository.RoleRepo;

@Service
public class RoleService {
	@Autowired
	private RoleRepo roleRepository;
}
