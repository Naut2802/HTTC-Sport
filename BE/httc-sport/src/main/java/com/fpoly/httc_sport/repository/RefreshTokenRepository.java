package com.fpoly.httc_sport.repository;

import com.fpoly.httc_sport.entity.RefreshTokenWhiteList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenWhiteList, String> {
	Optional<RefreshTokenWhiteList> findByToken(String token);
	void deleteByToken(String token);
}
