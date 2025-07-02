package io.zhc1.realworld.model;

import java.util.Optional;

public interface PaymentMethodRepository {
    Optional<PaymentMethod> findById(Integer id);
}
