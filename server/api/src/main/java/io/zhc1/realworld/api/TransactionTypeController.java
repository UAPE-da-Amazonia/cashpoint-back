package io.zhc1.realworld.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import io.zhc1.realworld.api.response.TransactionTypeResponse;
import io.zhc1.realworld.persistence.TransactionTypeJpaRepository;

@RestController
@RequiredArgsConstructor
class TransactionTypeController {
    private final TransactionTypeJpaRepository transactionTypeJpaRepository;

    /** GET /api/transaction-types - Listar todos os tipos de transação */
    @GetMapping("/api/transaction-types")
    TransactionTypeResponse getTransactionTypes() {
        var transactionTypes = transactionTypeJpaRepository.findAll();
        return new TransactionTypeResponse(transactionTypes);
    }

    /** GET /api/transaction-types/{id} - Obter tipo de transação específico */
    @GetMapping("/api/transaction-types/{id}")
    TransactionTypeResponse getTransactionType(@PathVariable Integer id) {
        var transactionType = transactionTypeJpaRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("TransactionType não encontrado"));
        return new TransactionTypeResponse(transactionType);
    }
}
