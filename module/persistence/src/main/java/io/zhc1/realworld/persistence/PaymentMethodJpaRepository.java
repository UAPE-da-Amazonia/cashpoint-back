package io.zhc1.realworld.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import io.zhc1.realworld.model.PaymentMethod;

public interface PaymentMethodJpaRepository extends JpaRepository<PaymentMethod, Integer> {}
