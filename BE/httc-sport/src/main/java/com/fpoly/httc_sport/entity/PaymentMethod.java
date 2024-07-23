package com.fpoly.httc_sport.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
	String method;
	float priceRate;
	
	@OneToMany(mappedBy = "paymentMethod")
	Set<RentInfo> rentInfoSet;
	
	@OneToMany(mappedBy = "paymentMethod")
	Set<Bill> billSet;
}
