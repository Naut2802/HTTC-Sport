package com.fpoly.httc_sport.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Wallet {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	String id;
	@Builder.Default
	int money = 0;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	User user;
	
	@OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	List<Transaction> transactions;
}
