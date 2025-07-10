package io.zhc1.realworld.api.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import io.zhc1.realworld.model.CashFlow;

public record CashFlowResponse(
        Integer id,
        BigDecimal amount,
        LocalDate transactionDate,
        String description,
        Integer paymentMethodId,
        Integer transactionTypeId,
        Integer categoryId,
        Integer accountTypeId,
        Long businessUnitId,
        ProfileInfo profile,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy,
        boolean isActive,
        boolean isChecked) {

    public record ProfileInfo(Integer id, String name, String email, String phone, String document) {

        public static ProfileInfo from(io.zhc1.realworld.model.Profile profile) {
            if (profile == null) {
                return null;
            }
            return new ProfileInfo(
                    profile.getId(), profile.getName(), profile.getEmail(), profile.getPhone(), profile.getDocument());
        }
    }

    public CashFlowResponse(CashFlow cashFlow) {
        this(
                cashFlow.getId(),
                cashFlow.getAmount(),
                cashFlow.getTransactionDate(),
                cashFlow.getDescription(),
                cashFlow.getPaymentMethodId(),
                cashFlow.getTransactionTypeId(),
                cashFlow.getCategoryId(),
                cashFlow.getAccountTypeId(),
                cashFlow.getBusinessUnitId(),
                ProfileInfo.from(cashFlow.getProfile()),
                cashFlow.getCreatedAt(),
                cashFlow.getUpdatedAt(),
                cashFlow.getCreatedBy(),
                cashFlow.getUpdatedBy(),
                cashFlow.isActive(),
                cashFlow.isChecked());
    }
}
