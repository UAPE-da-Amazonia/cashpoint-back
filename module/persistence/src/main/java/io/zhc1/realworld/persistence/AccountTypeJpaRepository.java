package io.zhc1.realworld.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import io.zhc1.realworld.model.AccountType;

public interface AccountTypeJpaRepository extends JpaRepository<AccountType, Integer> {}
