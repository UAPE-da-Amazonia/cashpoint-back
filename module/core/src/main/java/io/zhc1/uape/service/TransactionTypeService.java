package io.zhc1.uape.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import io.zhc1.uape.model.TransactionType;
import io.zhc1.uape.model.TransactionTypeRepository;

@Service
@RequiredArgsConstructor
public class TransactionTypeService {
    private final TransactionTypeRepository transactionTypeRepository;

    /**
     * Get transaction type by id.
     *
     * @param id transaction type id
     * @return Returns transaction type
     */
    public TransactionType getTransactionType(Integer id) {
        return transactionTypeRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("transaction type not found."));
    }

    /**
     * Get all transaction types.
     *
     * @return Returns list of transaction types
     */
    public List<TransactionType> getAllTransactionTypes() {
        return transactionTypeRepository.findAll();
    }

    /**
     * Create a new transaction type.
     *
     * @param name transaction type name
     * @return Returns the created transaction type
     */
    public TransactionType createTransactionType(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name is required.");
        }

        var transactionType = new TransactionType(name);
        return transactionTypeRepository.save(transactionType);
    }

    /**
     * Update a transaction type.
     *
     * @param id transaction type id
     * @param name transaction type name
     * @return Returns the updated transaction type
     */
    public TransactionType updateTransactionType(Integer id, String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name is required.");
        }

        TransactionType transactionType = getTransactionType(id);
        transactionType.setName(name);
        return transactionTypeRepository.save(transactionType);
    }

    /**
     * Delete a transaction type.
     *
     * @param id transaction type id
     */
    public void deleteTransactionType(Integer id) {
        TransactionType transactionType = getTransactionType(id);
        transactionTypeRepository.delete(transactionType);
    }
}
