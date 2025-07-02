package io.zhc1.realworld.persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.zhc1.realworld.model.BusinessUnit;
import io.zhc1.realworld.model.CashFlow;
import io.zhc1.realworld.model.Category;
import io.zhc1.realworld.model.TransactionType;

interface CashFlowJpaRepository extends JpaRepository<CashFlow, Integer> {

    List<CashFlow> findByBusinessUnit(BusinessUnit businessUnit);

    @Query(
            "SELECT cf FROM CashFlow cf WHERE cf.businessUnit = :businessUnit AND cf.transactionDate BETWEEN :startDate AND :endDate")
    List<CashFlow> findByBusinessUnitAndTransactionDateBetween(
            @Param("businessUnit") BusinessUnit businessUnit,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    List<CashFlow> findByBusinessUnitAndTransactionType(BusinessUnit businessUnit, TransactionType transactionType);

    List<CashFlow> findByBusinessUnitAndCategory(BusinessUnit businessUnit, Category category);

    Optional<CashFlow> findByIdAndIsActiveTrue(Integer id);

    List<CashFlow> findByBusinessUnitAndIsActiveTrue(BusinessUnit businessUnit);
}
