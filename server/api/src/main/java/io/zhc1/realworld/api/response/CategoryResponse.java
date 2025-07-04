package io.zhc1.realworld.api.response;

import java.util.Collection;
import java.util.List;

import io.zhc1.realworld.model.Category;

public record CategoryResponse(List<CategoryData> categories) {

    public record CategoryData(Integer id, String name, String transactionType, String businessUnit) {
        public CategoryData(Category category) {
            this(
                    category.getId(),
                    category.getName(),
                    category.getTransactionType().getName(),
                    category.getBusinessUnit().getName());
        }
    }

    public CategoryResponse(Collection<Category> categories) {
        this(categories.stream().map(CategoryData::new).toList());
    }

    public CategoryResponse(Category category) {
        this(List.of(new CategoryData(category)));
    }
}
