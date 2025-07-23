package io.zhc1.uape.api.response;

import java.util.Collection;
import java.util.List;

import io.zhc1.uape.model.Category;

public record CategoryResponse(List<CategoryData> categories) {

    public record CategoryData(Integer id, String name, String transactionTypeName, String businessUnitName) {
        public CategoryData(Category category) {
            this(
                    category.getId(),
                    category.getName(),
                    category.getTransactionType() != null
                            ? category.getTransactionType().getName()
                            : null,
                    category.getBusinessUnit() != null
                            ? category.getBusinessUnit().getName()
                            : null);
        }
    }

    public CategoryResponse(Collection<Category> categories) {
        this(categories.stream().map(CategoryData::new).toList());
    }

    public CategoryResponse(Category category) {
        this(List.of(new CategoryData(category)));
    }
}
