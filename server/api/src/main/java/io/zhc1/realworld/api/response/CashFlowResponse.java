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
        String paymentMethodName,
        String transactionTypeName,
        String categoryName,
        String accountTypeName,
        String businessUnitName,
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
                cashFlow.getPaymentMethod() != null ? cashFlow.getPaymentMethod().getName() : null,
                cashFlow.getTransactionType() != null ? cashFlow.getTransactionType().getName() : null,
                cashFlow.getCategory() != null ? cashFlow.getCategory().getName() : null,
                cashFlow.getAccountType() != null ? cashFlow.getAccountType().getName() : null,
                cashFlow.getBusinessUnit() != null ? cashFlow.getBusinessUnit().getName() : null,
                ProfileInfo.from(cashFlow.getProfile()),
                cashFlow.getCreatedAt(),
                cashFlow.getUpdatedAt(),
                cashFlow.getCreatedBy(),
                cashFlow.getUpdatedBy(),
                cashFlow.isActive(),
                cashFlow.isChecked());
    }
}
