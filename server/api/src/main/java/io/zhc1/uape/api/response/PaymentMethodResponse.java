package io.zhc1.uape.api.response;

import java.util.Collection;
import java.util.List;

import io.zhc1.uape.model.PaymentMethod;

public record PaymentMethodResponse(List<PaymentMethodData> paymentMethods) {

    public record PaymentMethodData(
            Integer id, String name, String description, String businessUnitName, boolean isActive) {
        public PaymentMethodData(PaymentMethod paymentMethod) {
            this(
                    paymentMethod.getId(),
                    paymentMethod.getName(),
                    paymentMethod.getDescription(),
                    paymentMethod.getBusinessUnit() != null
                            ? paymentMethod.getBusinessUnit().getName()
                            : null,
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
