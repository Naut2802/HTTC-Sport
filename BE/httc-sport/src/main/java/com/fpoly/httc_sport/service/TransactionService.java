package com.fpoly.httc_sport.service;

import com.fpoly.httc_sport.dto.response.TransactionResponse;
import com.fpoly.httc_sport.exception.AppException;
import com.fpoly.httc_sport.exception.ErrorCode;
import com.fpoly.httc_sport.mapper.TransactionMapper;
import com.fpoly.httc_sport.repository.TransactionRepository;
import com.fpoly.httc_sport.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionService {
	TransactionRepository transactionRepository;
	TransactionMapper transactionMapper;
	UserRepository userRepository;
	
	public List<TransactionResponse> getAllByUser(String userId) {
		var user = userRepository.findById(userId).orElseThrow(
				() -> new AppException(ErrorCode.USER_NOT_EXISTED)
		);
		
		var context = SecurityContextHolder.getContext();
		
		if (!user.getUsername().equals(context.getAuthentication().getName()))
			throw new AppException(ErrorCode.USER_NOT_EXISTED);
		
		var transactions = user.getWallet().getTransactions();
		
		return transactions.stream().map(transactionMapper::toTransactionResponse).toList();
	}
	public List<TransactionResponse> getAllTransactions() {
		return transactionRepository.findAll().stream().map(transactionMapper::toTransactionResponse).toList();
	}
}
