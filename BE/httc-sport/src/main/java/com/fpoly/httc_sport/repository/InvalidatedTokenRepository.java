package com.fpoly.httc_sport.repository;

import com.fpoly.httc_sport.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
	List<InvalidatedToken> findByExpiryTimeLessThan(Date date);
}
