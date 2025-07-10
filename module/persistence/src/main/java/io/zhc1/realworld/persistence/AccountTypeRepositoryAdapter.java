package io.zhc1.realworld.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

import io.zhc1.realworld.model.AccountType;
import io.zhc1.realworld.model.AccountTypeRepository;

@Repository
@RequiredArgsConstructor
public class AccountTypeRepositoryAdapter implements AccountTypeRepository {
    private final AccountTypeJpaRepository accountTypeJpaRepository;

    @Override
    public Optional<AccountType> findById(Integer id) {
        return accountTypeJpaRepository.findById(id);
    }

    @Override
    public List<AccountType> findAll() {
        return accountTypeJpaRepository.findAll();
    }

    @Override
    public List<AccountType> findByBusinessUnitId(Long businessUnitId) {
        return accountTypeJpaRepository.findByBusinessUnit_Id(businessUnitId);
    }

    @Override
    public AccountType save(AccountType accountType) {
        return accountTypeJpaRepository.save(accountType);
    }

    @Override
    public void delete(AccountType accountType) {
        accountTypeJpaRepository.delete(accountType);
    }
}
