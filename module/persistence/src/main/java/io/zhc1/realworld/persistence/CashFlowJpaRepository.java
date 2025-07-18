package io.zhc1.realworld.persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import io.zhc1.realworld.model.BusinessUnit;
import io.zhc1.realworld.model.CashFlow;
import io.zhc1.realworld.model.Category;
import io.zhc1.realworld.model.TransactionType;

interface CashFlowJpaRepository extends JpaRepository<CashFlow, Integer> {

    @EntityGraph(
            attributePaths = {"paymentMethod", "transactionType", "category", "accountType", "businessUnit", "profile"})
    @NonNull
    Optional<CashFlow> findById(@NonNull Integer id);

    @EntityGraph(
            attributePaths = {"paymentMethod", "transactionType", "category", "accountType", "businessUnit", "profile"})
    List<CashFlow> findByBusinessUnit(BusinessUnit businessUnit);

    @EntityGraph(
            attributePaths = {"paymentMethod", "transactionType", "category", "accountType", "businessUnit", "profile"})
    @Query(
            "SELECT cf FROM CashFlow cf WHERE cf.businessUnit = :businessUnit AND cf.transactionDate BETWEEN :startDate AND :endDate")
    List<CashFlow> findByBusinessUnitAndTransactionDateBetween(
            @Param("businessUnit") BusinessUnit businessUnit,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @EntityGraph(
            attributePaths = {"paymentMethod", "transactionType", "category", "accountType", "businessUnit", "profile"})
    List<CashFlow> findByBusinessUnitAndTransactionType(BusinessUnit businessUnit, TransactionType transactionType);

    @EntityGraph(
            attributePaths = {"paymentMethod", "transactionType", "category", "accountType", "businessUnit", "profile"})
    List<CashFlow> findByBusinessUnitAndCategory(BusinessUnit businessUnit, Category category);

    @EntityGraph(
            attributePaths = {"paymentMethod", "transactionType", "category", "accountType", "businessUnit", "profile"})
    Optional<CashFlow> findByIdAndIsActiveTrue(Integer id);

    @EntityGraph(
            attributePaths = {"paymentMethod", "transactionType", "category", "accountType", "businessUnit", "profile"})
    List<CashFlow> findByBusinessUnitAndIsActiveTrue(BusinessUnit businessUnit);
}
