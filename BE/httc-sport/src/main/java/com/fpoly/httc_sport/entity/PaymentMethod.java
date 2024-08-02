package com.fpoly.httc_sport.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import com.fpoly.httc_sport.utils.Enum.PaymentMethodEnum;

import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentMethod {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@Enumerated(EnumType.STRING)
	PaymentMethodEnum method;
	float priceRate;
	
	@OneToMany(mappedBy = "paymentMethod")
	Set<RentInfo> rentInfos;
	
	@OneToMany(mappedBy = "paymentMethod")
	Set<Bill> bills;
}
