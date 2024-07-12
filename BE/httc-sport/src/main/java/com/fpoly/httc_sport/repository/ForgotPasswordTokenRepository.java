package com.fpoly.httc_sport.repository;

import com.fpoly.httc_sport.entity.ForgotPasswordToken;
import com.fpoly.httc_sport.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForgotPasswordTokenRepository extends JpaRepository<ForgotPasswordToken, Long> {
	Optional<ForgotPasswordToken> findByToken(String token);
}
