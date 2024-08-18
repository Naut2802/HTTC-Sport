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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
	
	public List<TransactionResponse> getAllTransactions(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return transactionRepository.findAll(pageable).map(transactionMapper::toTransactionResponse).toList();
	}
	
	public List<TransactionResponse> getAllTransactionsByDate(LocalDate fromDate, LocalDate toDate, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		LocalDateTime fDate = LocalDateTime.of(fromDate.getYear(), fromDate.getMonth(), fromDate.getDayOfMonth(), 0, 0);
		LocalDateTime tDate = LocalDateTime.of(toDate.getYear(), toDate.getMonth(), toDate.getDayOfMonth(), 23, 59, 59);
		return transactionRepository.findByTransactionDateBetween(fDate, tDate, pageable).map(transactionMapper::toTransactionResponse).toList();
	}
	
	public List<TransactionResponse> getAllTransactionsByUser(String userId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return transactionRepository.findByUser(userId, pageable).map(transactionMapper::toTransactionResponse).toList();
	}
	
	public List<TransactionResponse> getAllTransactionsByUserAndDate(String userId, LocalDate fromDate, LocalDate toDate, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		LocalDateTime fDate = LocalDateTime.of(fromDate.getYear(), fromDate.getMonth(), fromDate.getDayOfMonth(), 0, 0);
		LocalDateTime tDate = LocalDateTime.of(toDate.getYear(), toDate.getMonth(), toDate.getDayOfMonth(), 23, 59, 59);
		return transactionRepository.findByUserWithDateBetween(userId, fDate, tDate, pageable).map(transactionMapper::toTransactionResponse).toList();
	}
}
