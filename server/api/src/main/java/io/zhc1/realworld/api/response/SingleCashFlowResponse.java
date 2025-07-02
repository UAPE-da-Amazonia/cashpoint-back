package io.zhc1.realworld.api.response;

import io.zhc1.realworld.model.CashFlow;

public record SingleCashFlowResponse(CashFlowResponse cashFlow) {
    public SingleCashFlowResponse(CashFlow cashFlow) {
        this(new CashFlowResponse(cashFlow));
    }
}
