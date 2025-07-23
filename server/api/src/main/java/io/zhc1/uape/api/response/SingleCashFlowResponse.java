package io.zhc1.uape.api.response;

import io.zhc1.uape.model.CashFlow;

public record SingleCashFlowResponse(CashFlowResponse cashFlow) {
    public SingleCashFlowResponse(CashFlow cashFlow) {
        this(new CashFlowResponse(cashFlow));
    }
}
