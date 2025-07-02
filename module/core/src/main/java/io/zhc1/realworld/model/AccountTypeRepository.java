package io.zhc1.realworld.model;

import java.util.Optional;

public interface AccountTypeRepository {
    Optional<AccountType> findById(Integer id);
}
