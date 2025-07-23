package io.zhc1.uape.api.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateCashFlowRequest(Params cashFlow) {
    public record Params(
            BigDecimal amount,
            LocalDate transactionDate,
            String description,
            Integer paymentMethodId,
            Integer transactionTypeId,
            Integer categoryId,
            Integer accountTypeId,
            Integer profileId) {}
}
