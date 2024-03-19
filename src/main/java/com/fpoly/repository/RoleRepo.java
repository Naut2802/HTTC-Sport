package com.fpoly.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.model.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
