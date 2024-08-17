package com.fpoly.httc_sport.repository;

import com.fpoly.httc_sport.utils.Enum.VipEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fpoly.httc_sport.entity.Vip;

import java.util.Optional;

public interface VipRepository extends JpaRepository<Vip, Long> {
	Optional<Vip> findByLevel(VipEnum level);
}
