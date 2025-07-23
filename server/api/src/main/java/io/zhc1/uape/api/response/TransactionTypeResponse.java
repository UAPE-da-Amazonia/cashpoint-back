package io.zhc1.uape.api.response;

import java.util.Collection;
import java.util.List;

import io.zhc1.uape.model.TransactionType;

public record TransactionTypeResponse(List<TransactionTypeData> transactionTypes) {

    public record TransactionTypeData(Integer id, String name) {
        public TransactionTypeData(TransactionType transactionType) {
            this(transactionType.getId(), transactionType.getName());
        }
    }

    public TransactionTypeResponse(Collection<TransactionType> transactionTypes) {
        this(transactionTypes.stream().map(TransactionTypeData::new).toList());
    }

    public TransactionTypeResponse(TransactionType transactionType) {
        this(List.of(new TransactionTypeData(transactionType)));
    }
}
