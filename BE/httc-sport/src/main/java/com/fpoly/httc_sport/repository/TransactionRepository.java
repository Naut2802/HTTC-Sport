package com.fpoly.httc_sport.repository;

import com.fpoly.httc_sport.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
	@Query("SELECT e FROM Transaction e WHERE e.wallet.user.id = :userId")
	Page<Transaction> findByUser(@Param("userId") String userId, Pageable pageable);
	@Query("SELECT e FROM Transaction e WHERE e.wallet.user.id = :userId AND e.transactionDate BETWEEN :startDate AND :endDate ")
	Page<Transaction> findByUserWithDateBetween(@Param("userId") String userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable pageable);
	Page<Transaction> findByTransactionDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
