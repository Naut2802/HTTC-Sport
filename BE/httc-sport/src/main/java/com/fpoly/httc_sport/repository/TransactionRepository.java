package com.fpoly.httc_sport.repository;

import com.fpoly.httc_sport.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
