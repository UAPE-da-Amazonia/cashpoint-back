package io.zhc1.realworld.api.response;

import java.util.Collection;
import java.util.List;

import io.zhc1.realworld.model.AccountType;

public record AccountTypeResponse(List<AccountTypeData> accountTypes) {

    public record AccountTypeData(Integer id, String name, Long businessUnitId) {
        public AccountTypeData(AccountType accountType) {
            this(
                    accountType.getId(),
                    accountType.getName(),
                    accountType.getBusinessUnit().getId());
        }
    }

    public AccountTypeResponse(Collection<AccountType> accountTypes) {
        this(accountTypes.stream().map(AccountTypeData::new).toList());
    }

    public AccountTypeResponse(AccountType accountType) {
        this(List.of(new AccountTypeData(accountType)));
    }
}
