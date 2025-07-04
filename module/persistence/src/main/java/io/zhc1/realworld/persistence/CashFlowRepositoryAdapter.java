package io.zhc1.realworld.persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

import io.zhc1.realworld.model.BusinessUnit;
import io.zhc1.realworld.model.CashFlow;
import io.zhc1.realworld.model.CashFlowRepository;
import io.zhc1.realworld.model.Category;
import io.zhc1.realworld.model.TransactionType;

@Repository
@RequiredArgsConstructor
class CashFlowRepositoryAdapter implements CashFlowRepository {
    private final CashFlowJpaRepository cashFlowJpaRepository;

    @Override
    public CashFlow save(CashFlow cashFlow) {
        return cashFlowJpaRepository.save(cashFlow);
    }

    @Override
    public Optional<CashFlow> findById(Integer id) {
        return cashFlowJpaRepository.findById(id);
    }

    @Override
    public List<CashFlow> findByBusinessUnit(BusinessUnit businessUnit) {
        return cashFlowJpaRepository.findByBusinessUnit(businessUnit);
    }

    @Override
    public List<CashFlow> findByBusinessUnitAndDateRange(
            BusinessUnit businessUnit, LocalDate startDate, LocalDate endDate) {
        return cashFlowJpaRepository.findByBusinessUnitAndTransactionDateBetween(businessUnit, startDate, endDate);
    }

    @Override
    public List<CashFlow> findByBusinessUnitAndTransactionType(
            BusinessUnit businessUnit, TransactionType transactionType) {
        return cashFlowJpaRepository.findByBusinessUnitAndTransactionType(businessUnit, transactionType);
    }

    @Override
    public List<CashFlow> findByBusinessUnitAndCategory(BusinessUnit businessUnit, Category category) {
        return cashFlowJpaRepository.findByBusinessUnitAndCategory(businessUnit, category);
    }

    @Override
    public void delete(CashFlow cashFlow) {
        cashFlowJpaRepository.delete(cashFlow);
    }

    @Override
    public boolean existsById(Integer id) {
        return cashFlowJpaRepository.existsById(id);
    }
}
