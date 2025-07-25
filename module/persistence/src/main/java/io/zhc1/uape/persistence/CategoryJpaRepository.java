package io.zhc1.uape.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import io.zhc1.uape.model.BusinessUnit;
import io.zhc1.uape.model.Category;
import io.zhc1.uape.model.TransactionType;

public interface CategoryJpaRepository extends JpaRepository<Category, Integer> {

    @EntityGraph(attributePaths = {"transactionType", "businessUnit"})
    List<Category> findByBusinessUnit(BusinessUnit businessUnit);

    @EntityGraph(attributePaths = {"transactionType", "businessUnit"})
    List<Category> findByBusinessUnitAndTransactionType(BusinessUnit businessUnit, TransactionType transactionType);

    @EntityGraph(attributePaths = {"transactionType", "businessUnit"})
    @NonNull Optional<Category> findById(@NonNull Integer id);
}
