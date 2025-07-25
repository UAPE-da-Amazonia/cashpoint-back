package io.zhc1.uape.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import io.zhc1.uape.model.PaymentMethod;

public interface PaymentMethodJpaRepository extends JpaRepository<PaymentMethod, Integer> {

    @EntityGraph(attributePaths = {"businessUnit"})
    List<PaymentMethod> findByBusinessUnit_Id(Long businessUnitId);

    @EntityGraph(attributePaths = {"businessUnit"})
    List<PaymentMethod> findByBusinessUnit_IdAndIsActiveTrue(Long businessUnitId);
}
