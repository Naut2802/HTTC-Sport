package com.fpoly.httc_sport.repository;

import com.fpoly.httc_sport.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, String> {
}
