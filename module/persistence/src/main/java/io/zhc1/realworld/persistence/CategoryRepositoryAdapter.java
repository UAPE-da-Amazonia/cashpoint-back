package io.zhc1.realworld.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

import io.zhc1.realworld.model.BusinessUnit;
import io.zhc1.realworld.model.Category;
import io.zhc1.realworld.model.CategoryRepository;
import io.zhc1.realworld.model.TransactionType;

@Repository
@RequiredArgsConstructor
class CategoryRepositoryAdapter implements CategoryRepository {
    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public Category save(Category category) {
        return categoryJpaRepository.save(category);
    }

    @Override
    public Optional<Category> findById(Integer id) {
        return categoryJpaRepository.findById(id);
    }

    @Override
    public List<Category> findByBusinessUnit(BusinessUnit businessUnit) {
        return categoryJpaRepository.findByBusinessUnit(businessUnit);
    }

    @Override
    public List<Category> findByBusinessUnitAndTransactionType(
            BusinessUnit businessUnit, TransactionType transactionType) {
        return categoryJpaRepository.findByBusinessUnitAndTransactionType(businessUnit, transactionType);
    }

    @Override
    public void delete(Category category) {
        categoryJpaRepository.delete(category);
    }

    @Override
    public boolean existsById(Integer id) {
        return categoryJpaRepository.existsById(id);
    }
}
