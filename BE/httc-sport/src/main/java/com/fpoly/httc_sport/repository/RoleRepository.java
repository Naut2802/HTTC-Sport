package com.fpoly.httc_sport.repository;

import com.fpoly.httc_sport.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
	boolean existsByRoleName(String roleName);
	Optional<Role> findByRoleName(String roleName);
	List<Role> findAllByRoleNameIn(Iterable<String> names);
}
