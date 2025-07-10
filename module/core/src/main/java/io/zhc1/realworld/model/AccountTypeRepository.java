package io.zhc1.realworld.model;

import java.util.List;
import java.util.Optional;

public interface AccountTypeRepository {
    Optional<AccountType> findById(Integer id);

    List<AccountType> findAll();

    List<AccountType> findByBusinessUnitId(Long businessUnitId);

    AccountType save(AccountType accountType);

    void delete(AccountType accountType);
}
