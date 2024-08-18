package com.fpoly.httc_sport.controller;

import com.fpoly.httc_sport.dto.response.ApiResponse;
import com.fpoly.httc_sport.dto.response.TransactionResponse;
import com.fpoly.httc_sport.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/v1/transaction")
@Tag(name = "Transaction Controller")
public class TransactionController {
	TransactionService transactionService;
	
	@Operation(summary = "Get transactions by user")
	@GetMapping("{userId}")
	ApiResponse<List<TransactionResponse>> getTransactionsByUser(@PathVariable String userId) {
		return ApiResponse.<List<TransactionResponse>>builder()
				.result(transactionService.getAllByUser(userId))
				.build();
	}
	
	@Operation(summary = "Get transactions by admin")
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<List<TransactionResponse>> getAllTransactions(@RequestParam(defaultValue = "0") int page,
	                                                          @RequestParam(defaultValue = "5") int size) {
		return ApiResponse.<List<TransactionResponse>>builder()
				.result(transactionService.getAllTransactions(page, size))
				.build();
	}
	
	@Operation(summary = "Get transactions with fromDate and toDate", description = "Api for admin")
	@GetMapping("{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<List<TransactionResponse>> getAllTransactionsByDate(@RequestParam LocalDate fromDate,
	                                                                @RequestParam LocalDate toDate,
	                                                                @RequestParam(defaultValue = "0") int page,
	                                                                @RequestParam(defaultValue = "5") int size) {
		return ApiResponse.<List<TransactionResponse>>builder()
				.result(transactionService.getAllTransactionsByDate(fromDate, toDate, page, size))
				.build();
	}
	
	@Operation(summary = "Get transactions by user")
	@GetMapping("{userId}")
	ApiResponse<List<TransactionResponse>> getAllTransactionsByUser(@PathVariable String userId,
	                                                          @RequestParam(defaultValue = "0") int page,
	                                                          @RequestParam(defaultValue = "5") int size) {
		return ApiResponse.<List<TransactionResponse>>builder()
				.result(transactionService.getAllTransactionsByUser(userId, page, size))
				.build();
	}
	
	@Operation(summary = "Get transactions with fromDate and toDate by user")
	@GetMapping("{userId}")
	ApiResponse<List<TransactionResponse>> getAllTransactionsByUserAndDate(@PathVariable String userId,
	                                                          @RequestParam LocalDate fromDate,
	                                                          @RequestParam LocalDate toDate,
	                                                          @RequestParam(defaultValue = "0") int page,
	                                                          @RequestParam(defaultValue = "5") int size) {
		return ApiResponse.<List<TransactionResponse>>builder()
				.result(transactionService.getAllTransactionsByUserAndDate(userId, fromDate, toDate, page, size))
				.build();
	}
}
