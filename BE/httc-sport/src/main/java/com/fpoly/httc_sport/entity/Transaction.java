package com.fpoly.httc_sport.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fpoly.httc_sport.utils.Enum.TransactionTypeEnum;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	String id;
	long total;
	Date createdAt;
	TransactionTypeEnum transactionType;
	boolean paymentStatus;
	
	@ManyToOne
	@JoinColumn(name = "wallet_id")
	Wallet wallet;
	@OneToOne(mappedBy = "transaction", fetch = FetchType.EAGER)
	RentInfo rentInfo;
}
