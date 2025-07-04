package io.zhc1.realworld.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.zhc1.realworld.model.PaymentMethod;

public interface PaymentMethodJpaRepository extends JpaRepository<PaymentMethod, Integer> {

    List<PaymentMethod> findByBusinessUnit_Id(Long businessUnitId);

    List<PaymentMethod> findByBusinessUnit_IdAndIsActiveTrue(Long businessUnitId);
}
