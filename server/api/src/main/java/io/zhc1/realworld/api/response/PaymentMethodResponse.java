package io.zhc1.realworld.api.response;

import java.util.Collection;
import java.util.List;

import io.zhc1.realworld.model.PaymentMethod;

public record PaymentMethodResponse(List<PaymentMethodData> paymentMethods) {

    public record PaymentMethodData(
            Integer id, String name, String description, Long businessUnitId, boolean isActive) {
        public PaymentMethodData(PaymentMethod paymentMethod) {
            this(
                    paymentMethod.getId(),
                    paymentMethod.getName(),
                    paymentMethod.getDescription(),
                    paymentMethod.getBusinessUnitId(),
                    paymentMethod.isActive());
        }
    }

    public PaymentMethodResponse(Collection<PaymentMethod> paymentMethods) {
        this(paymentMethods.stream().map(PaymentMethodData::new).toList());
    }

    public PaymentMethodResponse(PaymentMethod paymentMethod) {
        this(List.of(new PaymentMethodData(paymentMethod)));
    }
}
