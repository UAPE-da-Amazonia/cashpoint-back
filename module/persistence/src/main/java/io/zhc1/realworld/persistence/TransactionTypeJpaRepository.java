package io.zhc1.realworld.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import io.zhc1.realworld.model.TransactionType;

public interface TransactionTypeJpaRepository extends JpaRepository<TransactionType, Integer> {}
