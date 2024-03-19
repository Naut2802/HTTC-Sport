package com.fpoly.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fpoly.model.Vip;

public interface VipRepo extends JpaRepository<Vip, Integer> {
}
