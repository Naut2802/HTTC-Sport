package com.fpoly.httc_sport.repository;

import java.util.Optional;

import com.fpoly.httc_sport.utils.Enum.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.httc_sport.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
	int countAllByRolesRoleNameNot(RoleEnum role);
}
