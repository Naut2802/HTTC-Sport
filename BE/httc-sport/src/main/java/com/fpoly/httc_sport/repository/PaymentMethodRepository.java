package com.fpoly.httc_sport.repository;

import com.fpoly.httc_sport.entity.PaymentMethod;
import com.fpoly.httc_sport.utils.Enum.PaymentMethodEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
	Optional<PaymentMethod> findByMethod(PaymentMethodEnum method);
	boolean existsByMethod(PaymentMethodEnum method);
}
