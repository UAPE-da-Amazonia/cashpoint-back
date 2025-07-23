package io.zhc1.uape.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import io.zhc1.uape.api.request.TransactionTypeRequest;
import io.zhc1.uape.api.response.TransactionTypeResponse;
import io.zhc1.uape.model.TransactionType;
import io.zhc1.uape.service.TransactionTypeService;

@RestController
@RequestMapping("/api/transaction-types")
@RequiredArgsConstructor
class TransactionTypeController {
    private final TransactionTypeService transactionTypeService;

    /** GET /api/transaction-types - Listar todos os tipos de transação */
    @GetMapping
    TransactionTypeResponse getTransactionTypes() {
        var transactionTypes = transactionTypeService.getAllTransactionTypes();
        return new TransactionTypeResponse(transactionTypes);
    }

    /** GET /api/transaction-types/{id} - Obter tipo de transação específico */
    @GetMapping("/{id}")
    TransactionTypeResponse getTransactionType(@PathVariable Integer id) {
        var transactionType = transactionTypeService.getTransactionType(id);
        return new TransactionTypeResponse(transactionType);
    }

    /** POST /api/transaction-types - Criar novo tipo de transação */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    TransactionTypeResponse createTransactionType(@RequestBody TransactionTypeRequest request) {
        TransactionType savedTransactionType = transactionTypeService.createTransactionType(request.name());
        return new TransactionTypeResponse(savedTransactionType);
    }

    /** PUT /api/transaction-types/{id} - Atualizar tipo de transação */
    @PutMapping("/{id}")
    ResponseEntity<TransactionTypeResponse> updateTransactionType(
            @PathVariable Integer id, @RequestBody TransactionTypeRequest request) {

        TransactionType updatedTransactionType = transactionTypeService.updateTransactionType(id, request.name());
        return ResponseEntity.ok(new TransactionTypeResponse(updatedTransactionType));
    }

    /** DELETE /api/transaction-types/{id} - Deletar tipo de transação */
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteTransactionType(@PathVariable Integer id) {
        transactionTypeService.deleteTransactionType(id);
        return ResponseEntity.noContent().build();
    }
}
