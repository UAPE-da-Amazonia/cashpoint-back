package io.zhc1.uape.model;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    Category save(Category category);

    Optional<Category> findById(Integer id);

    List<Category> findByBusinessUnit(BusinessUnit businessUnit);

    List<Category> findByBusinessUnitAndTransactionType(BusinessUnit businessUnit, TransactionType transactionType);

    void delete(Category category);

    boolean existsById(Integer id);

    Optional<Category> findByIdWithRelations(Integer id);
}
