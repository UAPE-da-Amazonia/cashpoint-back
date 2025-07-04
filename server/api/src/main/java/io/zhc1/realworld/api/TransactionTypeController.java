package io.zhc1.realworld.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import io.zhc1.realworld.api.request.TransactionTypeRequest;
import io.zhc1.realworld.api.response.TransactionTypeResponse;
import io.zhc1.realworld.model.TransactionType;
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

    /** POST /api/transaction-types - Criar novo tipo de transação */
    @PostMapping("/api/transaction-types")
    @ResponseStatus(HttpStatus.CREATED)
    TransactionTypeResponse createTransactionType(@RequestBody TransactionTypeRequest request) {
        TransactionType transactionType = new TransactionType(request.name());
        TransactionType savedTransactionType = transactionTypeJpaRepository.save(transactionType);
        return new TransactionTypeResponse(savedTransactionType);
    }

    /** PUT /api/transaction-types/{id} - Atualizar tipo de transação */
    @PutMapping("/api/transaction-types/{id}")
    ResponseEntity<TransactionTypeResponse> updateTransactionType(
            @PathVariable Integer id, @RequestBody TransactionTypeRequest request) {

        TransactionType existingTransactionType = transactionTypeJpaRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("TransactionType não encontrado"));

        existingTransactionType.setName(request.name());
        TransactionType updatedTransactionType = transactionTypeJpaRepository.save(existingTransactionType);
        return ResponseEntity.ok(new TransactionTypeResponse(updatedTransactionType));
    }

    /** DELETE /api/transaction-types/{id} - Deletar tipo de transação */
    @DeleteMapping("/api/transaction-types/{id}")
    ResponseEntity<Void> deleteTransactionType(@PathVariable Integer id) {
        TransactionType transactionType = transactionTypeJpaRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("TransactionType não encontrado"));

        transactionTypeJpaRepository.delete(transactionType);
        return ResponseEntity.noContent().build();
    }
}
