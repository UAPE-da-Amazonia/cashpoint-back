package io.zhc1.realworld.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

import io.zhc1.realworld.model.TransactionType;
import io.zhc1.realworld.model.TransactionTypeRepository;

@Repository
@RequiredArgsConstructor
public class TransactionTypeRepositoryAdapter implements TransactionTypeRepository {
    private final TransactionTypeJpaRepository transactionTypeJpaRepository;

    @Override
    public Optional<TransactionType> findById(Integer id) {
        return transactionTypeJpaRepository.findById(id);
    }

    public List<TransactionType> findAll() {
        return transactionTypeJpaRepository.findAll();
    }

    public TransactionType save(TransactionType transactionType) {
        return transactionTypeJpaRepository.save(transactionType);
    }

    public void delete(TransactionType transactionType) {
        transactionTypeJpaRepository.delete(transactionType);
    }
}
