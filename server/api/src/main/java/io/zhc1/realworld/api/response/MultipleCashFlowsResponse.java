package io.zhc1.realworld.api.response;

import java.util.List;

import io.zhc1.realworld.model.CashFlow;

public record MultipleCashFlowsResponse(List<CashFlowResponse> cashFlows) {
    public static MultipleCashFlowsResponse from(List<CashFlow> cashFlows) {
        return new MultipleCashFlowsResponse(
                cashFlows.stream().map(CashFlowResponse::new).toList());
    }
}
