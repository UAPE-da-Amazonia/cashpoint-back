package io.zhc1.realworld.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import io.zhc1.realworld.model.BusinessUnit;
import io.zhc1.realworld.model.Category;
import io.zhc1.realworld.model.TransactionType;

public interface CategoryJpaRepository extends JpaRepository<Category, Integer> {

    @EntityGraph(attributePaths = {"transactionType", "businessUnit"})
    List<Category> findByBusinessUnit(BusinessUnit businessUnit);

    @EntityGraph(attributePaths = {"transactionType", "businessUnit"})
    List<Category> findByBusinessUnitAndTransactionType(BusinessUnit businessUnit, TransactionType transactionType);

    @EntityGraph(attributePaths = {"transactionType", "businessUnit"})
    Optional<Category> findById(Integer id);
}
