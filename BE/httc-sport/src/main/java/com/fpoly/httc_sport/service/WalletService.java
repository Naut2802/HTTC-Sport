package com.fpoly.httc_sport.service;

import com.fpoly.httc_sport.entity.Transaction;
import com.fpoly.httc_sport.entity.Wallet;
import com.fpoly.httc_sport.repository.WalletRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletService {
	WalletRepository walletRepository;
	
	public Transaction CreateTransaction(Wallet wallet) {
		return Transaction.builder().build();
	}
}
