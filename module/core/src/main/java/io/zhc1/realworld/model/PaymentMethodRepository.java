package io.zhc1.realworld.model;

import java.util.List;
import java.util.Optional;

public interface PaymentMethodRepository {
    Optional<PaymentMethod> findById(Integer id);

    List<PaymentMethod> findByBusinessUnitId(Long businessUnitId);

    PaymentMethod save(PaymentMethod paymentMethod);

    void delete(PaymentMethod paymentMethod);
}
