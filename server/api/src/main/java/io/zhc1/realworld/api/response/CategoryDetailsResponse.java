package io.zhc1.realworld.api.response;

import io.zhc1.realworld.model.Category;

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
