package com.fpoly.httc_sport.entity;

import com.fpoly.httc_sport.utils.Enum.TransactionTypeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction extends AbstractEntity {
	@Id
	Integer id;
	int paymentAmount;
	LocalDateTime transactionDate;
	@Enumerated(EnumType.STRING)
	TransactionTypeEnum transactionType;
	@Builder.Default
	boolean paymentStatus = false;
	
	@ManyToOne
	@JoinColumn(name = "wallet_id")
	Wallet wallet;
	
	@PrePersist
	void generateId() {
		String uuid = UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 6);
		this.id = Integer.parseInt(uuid);
	}
}
