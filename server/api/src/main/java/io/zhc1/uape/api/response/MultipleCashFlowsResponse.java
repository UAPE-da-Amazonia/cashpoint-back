package io.zhc1.uape.api.response;

import java.util.List;

import io.zhc1.uape.model.CashFlow;

public record MultipleCashFlowsResponse(List<CashFlowResponse> cashFlows) {
    public static MultipleCashFlowsResponse from(List<CashFlow> cashFlows) {
        return new MultipleCashFlowsResponse(
                cashFlows.stream().map(CashFlowResponse::new).toList());
    }
}
