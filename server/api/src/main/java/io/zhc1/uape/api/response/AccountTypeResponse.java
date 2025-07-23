package io.zhc1.uape.api.response;

import java.util.Collection;
import java.util.List;

import io.zhc1.uape.model.AccountType;

public record AccountTypeResponse(List<AccountTypeData> accountTypes) {

    public record AccountTypeData(Integer id, String name, String businessUnitName) {
        public AccountTypeData(AccountType accountType) {
            this(
                    accountType.getId(),
                    accountType.getName(),
                    accountType.getBusinessUnit() != null
                            ? accountType.getBusinessUnit().getName()
                            : null);
        }
    }

    public AccountTypeResponse(Collection<AccountType> accountTypes) {
        this(accountTypes.stream().map(AccountTypeData::new).toList());
    }

    public AccountTypeResponse(AccountType accountType) {
        this(List.of(new AccountTypeData(accountType)));
    }
}
