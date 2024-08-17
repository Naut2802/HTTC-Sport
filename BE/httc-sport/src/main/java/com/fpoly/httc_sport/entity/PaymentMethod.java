package com.fpoly.httc_sport.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import com.fpoly.httc_sport.utils.Enum.PaymentMethodEnum;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentMethod extends AbstractIdEntity {
	@Enumerated(EnumType.STRING)
	PaymentMethodEnum method;
	float priceRate;
	
	@OneToMany(mappedBy = "paymentMethod")
	List<RentInfo> rentInfos;
	
	@OneToMany(mappedBy = "paymentMethod")
	List<Bill> bills;
}
