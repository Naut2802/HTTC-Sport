package com.fpoly.httc_sport.repository;

import com.fpoly.httc_sport.entity.Role;
import com.fpoly.httc_sport.utils.Enum.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
	List<Role> findByRoleNameIn(Iterable<RoleEnum> roleNames);
	Optional<Role> findByRoleName(RoleEnum roleName);
	boolean existsByRoleName(RoleEnum roleName);
}
