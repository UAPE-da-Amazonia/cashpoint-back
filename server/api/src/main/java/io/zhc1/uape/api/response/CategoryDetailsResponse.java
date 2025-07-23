package io.zhc1.uape.api.response;

import io.zhc1.uape.model.Category;

public record CategoryDetailsResponse(Integer id, String name, String transactionTypeName, String businessUnitName) {
    public CategoryDetailsResponse(Category category) {
        this(
                category.getId(),
                category.getName(),
                category.getTransactionType() != null
                        ? category.getTransactionType().getName()
                        : null,
                category.getBusinessUnit() != null ? category.getBusinessUnit().getName() : null);
    }
}
