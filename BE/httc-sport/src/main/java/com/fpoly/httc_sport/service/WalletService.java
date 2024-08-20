package com.fpoly.httc_sport.service;

import com.fpoly.httc_sport.dto.request.TransactionRequest;
import com.fpoly.httc_sport.dto.response.TransactionResponse;
import com.fpoly.httc_sport.entity.MailInfo;
import com.fpoly.httc_sport.entity.RentInfo;
import com.fpoly.httc_sport.entity.Transaction;
import com.fpoly.httc_sport.entity.Wallet;
import com.fpoly.httc_sport.exception.AppException;
import com.fpoly.httc_sport.exception.ErrorCode;
import com.fpoly.httc_sport.mapper.TransactionMapper;
import com.fpoly.httc_sport.repository.TransactionRepository;
import com.fpoly.httc_sport.repository.UserRepository;
import com.fpoly.httc_sport.repository.WalletRepository;
import com.fpoly.httc_sport.utils.Enum.TransactionTypeEnum;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletService {
	WalletRepository walletRepository;
	TransactionRepository transactionRepository;
	TransactionMapper transactionMapper;
	UserRepository userRepository;
	
	PaymentService paymentService;
	MailerService mailerService;
	
	public TransactionResponse createTopUpTransaction(TransactionRequest request) {
		var wallet = walletRepository.findById(request.getWalletId()).orElseThrow(
				() -> new AppException(ErrorCode.WALLET_NOT_EXISTED)
		);
		
		var context = SecurityContextHolder.getContext();
		var user = wallet.getUser();
		
		if (!user.getUsername().equals(context.getAuthentication().getName()))
			throw new AppException(ErrorCode.UNAUTHENTICATED);
		
		Transaction transaction = Transaction.builder()
				.transactionDate(LocalDateTime.now())
				.wallet(wallet)
				.transactionType(TransactionTypeEnum.DEPOSIT.getValue())
				.paymentAmount(request.getPaymentAmount())
				.build();
		
		var response = transactionMapper.toTransactionResponse(transactionRepository.save(transaction));
		response.setMessage("Vui lòng thanh toán để hoàn tất giao dịch");
		return response;
	}
	
	@Transactional
	public TransactionResponse confirmTopUpTransaction(String code, String id, String status) {
		var paymentInfo = paymentService.getPaymentInfo(id);
		
		if (paymentInfo.getData() == null)
			throw new AppException(ErrorCode.PAYMENT_NOT_EXISTED);
		
		var transaction = transactionRepository.findById(paymentInfo.getData().getOrderCode()).orElseThrow(
				() -> new AppException(ErrorCode.TRANSACTION_NOT_EXISTED)
		);
		
		if (!code.equals("00") || !status.equals("PAID")) {
			transactionRepository.delete(transaction);
			return TransactionResponse.builder().message("Thanh toán thất bại").build();
		}
		var wallet = transaction.getWallet();
		
		transaction.setPaymentStatus(true);
		
		int total = wallet.getMoney() + transaction.getPaymentAmount();
		wallet.setMoney(total);
		walletRepository.save(wallet);
		
		var response = transactionMapper.toTransactionResponse(transactionRepository.save(transaction));
		response.setMessage("Thanh toán giao dịch thành công");
		
		MailInfo mailInfo = MailInfo.builder()
				.to(wallet.getUser().getEmail())
				.subject("Thông báo nạp tiền vào ví")
				.body(mailerService.generateDepositBody(wallet.getUser().getEmail(),
						wallet.getUser().getUsername(),
						transaction.getPaymentAmount(),
						wallet.getMoney()))
				.build();
		
		mailerService.queue(mailInfo);
		
		return response;
	}
	
	@Transactional
	public void createPitchPaymentTransaction(Wallet wallet, RentInfo rentInfo) {
		if (wallet.getMoney() < rentInfo.getTotal())
			throw new AppException(ErrorCode.WALLET_NOT_ENOUGH);
		
		int totalRemaining = wallet.getMoney() - rentInfo.getTotal();
		wallet.setMoney(totalRemaining);
		
		Transaction transaction = Transaction.builder()
				.paymentAmount(rentInfo.getTotal())
				.transactionDate(LocalDateTime.now())
				.transactionType(TransactionTypeEnum.PAY.getValue())
				.paymentStatus(true)
				.wallet(wallet)
				.rentInfo(rentInfo)
				.build();
		
		rentInfo.setTransaction(transaction);
		walletRepository.save(wallet);
		transactionRepository.save(transaction);
	}
	
	@Transactional
	public void createPitchPayRemainingAmountTransaction(Wallet wallet, RentInfo rentInfo) {
		var remainingAmount = rentInfo.getTotal() - rentInfo.getDeposit();
		if (wallet.getMoney() < remainingAmount)
			throw new AppException(ErrorCode.WALLET_NOT_ENOUGH);
		
		int totalRemaining = wallet.getMoney() - remainingAmount;
		wallet.setMoney(totalRemaining);
		
		Transaction transaction = Transaction.builder()
				.paymentAmount(rentInfo.getTotal())
				.transactionDate(LocalDateTime.now())
				.transactionType(TransactionTypeEnum.PAY_REMAINING.getValue())
				.paymentStatus(true)
				.wallet(wallet)
				.rentInfo(rentInfo)
				.build();
		
		rentInfo.setTransaction(transaction);
		walletRepository.save(wallet);
		transactionRepository.save(transaction);
	}
	
	public TransactionResponse adminTopUpUser(String userId, TransactionRequest request) {
		var user = userRepository.findById(userId).orElseThrow(
				() -> new AppException(ErrorCode.USER_NOT_EXISTED)
		);
		
		var wallet = walletRepository.findById(request.getWalletId()).orElseThrow(
				() -> new AppException(ErrorCode.WALLET_NOT_EXISTED)
		);
		
		if (!user.getId().equals(wallet.getUser().getId()))
			throw new AppException(ErrorCode.WALLET_NOT_EXISTED);
		
		Transaction transaction = Transaction.builder()
				.paymentAmount(request.getPaymentAmount())
				.transactionDate(LocalDateTime.now())
				.paymentStatus(true)
				.transactionType(TransactionTypeEnum.ADMIN_DEPOSIT.getValue())
				.wallet(wallet)
				.build();
		
		int total = wallet.getMoney() + transaction.getPaymentAmount();
		wallet.setMoney(total);
		walletRepository.save(wallet);
		
		var response = transactionMapper.toTransactionResponse(transactionRepository.save(transaction));
		response.setMessage("Nạp tiền vào ví user " + user.getUsername() + " thành công");
		
		MailInfo mailInfo = MailInfo.builder()
				.to(user.getEmail())
				.subject("Thông báo nạp tiền vào ví")
				.body(mailerService.generateDepositBody(user.getEmail(),
						user.getUsername(),
						transaction.getPaymentAmount(),
						wallet.getMoney()))
				.build();
		
		mailerService.queue(mailInfo);
		
		return response;
	}
}
