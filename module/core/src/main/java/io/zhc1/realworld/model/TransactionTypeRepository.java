package io.zhc1.realworld.model;

import java.util.List;
import java.util.Optional;

public interface TransactionTypeRepository {
    Optional<TransactionType> findById(Integer id);

    List<TransactionType> findAll();

    TransactionType save(TransactionType transactionType);

    void delete(TransactionType transactionType);
}
