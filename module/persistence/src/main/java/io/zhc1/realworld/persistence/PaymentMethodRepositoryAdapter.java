package io.zhc1.realworld.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

import io.zhc1.realworld.model.PaymentMethod;
import io.zhc1.realworld.model.PaymentMethodRepository;

@Repository
@RequiredArgsConstructor
public class PaymentMethodRepositoryAdapter implements PaymentMethodRepository {
    private final PaymentMethodJpaRepository paymentMethodJpaRepository;

    @Override
    public Optional<PaymentMethod> findById(Integer id) {
        return paymentMethodJpaRepository.findById(id);
    }

    @Override
    public List<PaymentMethod> findByBusinessUnitId(Long businessUnitId) {
        return paymentMethodJpaRepository.findByBusinessUnit_Id(businessUnitId);
    }

    @Override
    public PaymentMethod save(PaymentMethod paymentMethod) {
        return paymentMethodJpaRepository.save(paymentMethod);
    }

    @Override
    public void delete(PaymentMethod paymentMethod) {
        paymentMethodJpaRepository.delete(paymentMethod);
    }
}
