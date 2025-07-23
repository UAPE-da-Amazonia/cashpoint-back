package io.zhc1.uape.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import io.zhc1.uape.model.TransactionType;

public interface TransactionTypeJpaRepository extends JpaRepository<TransactionType, Integer> {}
