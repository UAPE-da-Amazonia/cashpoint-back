package io.zhc1.uape.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CashFlowRepository {
    CashFlow save(CashFlow cashFlow);

    Optional<CashFlow> findById(Integer id);

    List<CashFlow> findByBusinessUnit(BusinessUnit businessUnit);

    List<CashFlow> findByBusinessUnitAndDateRange(BusinessUnit businessUnit, LocalDate startDate, LocalDate endDate);

    List<CashFlow> findByBusinessUnitAndTransactionType(BusinessUnit businessUnit, TransactionType transactionType);

    List<CashFlow> findByBusinessUnitAndCategory(BusinessUnit businessUnit, Category category);

    void delete(CashFlow cashFlow);

    boolean existsById(Integer id);
}
