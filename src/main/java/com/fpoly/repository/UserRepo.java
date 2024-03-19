package com.fpoly.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.model.User;

public interface UserRepo extends JpaRepository<User, String> {
    User findUserByEmail(String email);
    Boolean existsByEmail(String email);
    List<User> findUserByRolesRoleNameLike(String roleName);
}
