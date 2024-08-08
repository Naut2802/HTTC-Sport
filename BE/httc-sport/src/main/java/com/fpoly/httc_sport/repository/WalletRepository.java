package com.fpoly.httc_sport.repository;

import com.fpoly.httc_sport.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, String> {
}
