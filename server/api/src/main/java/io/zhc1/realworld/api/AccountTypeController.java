package io.zhc1.realworld.api;

import java.util.List;

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

import io.zhc1.realworld.api.request.AccountTypeRequest;
import io.zhc1.realworld.api.response.AccountTypeResponse;
import io.zhc1.realworld.config.AuthToken;
import io.zhc1.realworld.model.AccountType;
import io.zhc1.realworld.model.BusinessUnit;
import io.zhc1.realworld.persistence.AccountTypeJpaRepository;
import io.zhc1.realworld.service.BusinessUnitService;

@RestController
@RequestMapping("/api/account-types")
@RequiredArgsConstructor
public class AccountTypeController {

    private final AccountTypeJpaRepository accountTypeJpaRepository;
    private final BusinessUnitService businessUnitService;

    /** GET /api/account-types - Listar todos os tipos de conta (apenas id e name) */
    @GetMapping
    public ResponseEntity<AccountTypeResponse> getAccountTypes() {
        List<AccountType> accountTypes = accountTypeJpaRepository.findAll();
        return ResponseEntity.ok(new AccountTypeResponse(accountTypes));
    }

    /** GET /api/account-types/{id} - Obter tipo de conta específico */
    @GetMapping("/{id}")
    public ResponseEntity<AccountTypeResponse> getAccountType(AuthToken authToken, @PathVariable Integer id) {

        AccountType accountType = accountTypeJpaRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("AccountType não encontrado"));

        // Verificar se o usuário tem acesso ao tipo de conta
        if (!authToken.isAdmin() && !accountType.getBusinessUnit().getId().equals(authToken.businessUnitId())) {
            throw new SecurityException("Access denied. You can only access account types from your business unit.");
        }

        return ResponseEntity.ok(new AccountTypeResponse(accountType));
    }

    /** POST /api/account-types - Criar novo tipo de conta */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AccountTypeResponse> createAccountType(
            AuthToken authToken, @RequestBody AccountTypeRequest request) {

        // Se não especificar businessUnit, usa a do usuário logado
        Long businessUnitId = request.businessUnitId() != null ? request.businessUnitId() : authToken.businessUnitId();

        // Verificar se o usuário tem acesso à unidade de negócio
        if (!authToken.isAdmin() && !businessUnitId.equals(authToken.businessUnitId())) {
            throw new SecurityException("Access denied. You can only create account types for your business unit.");
        }

        // Verificar se a unidade de negócio existe
        BusinessUnit businessUnit = businessUnitService
                .findById(businessUnitId)
                .orElseThrow(() -> new RuntimeException("BusinessUnit não encontrada"));

        AccountType accountType = new AccountType(request.name(), businessUnit);
        AccountType savedAccountType = accountTypeJpaRepository.save(accountType);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AccountTypeResponse(savedAccountType));
    }

    /** PUT /api/account-types/{id} - Atualizar tipo de conta */
    @PutMapping("/{id}")
    public ResponseEntity<AccountTypeResponse> updateAccountType(
            AuthToken authToken, @PathVariable Integer id, @RequestBody AccountTypeRequest request) {

        AccountType existingAccountType = accountTypeJpaRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("AccountType não encontrado"));

        // Verificar se o usuário tem acesso ao tipo de conta
        if (!authToken.isAdmin()
                && !existingAccountType.getBusinessUnit().getId().equals(authToken.businessUnitId())) {
            throw new SecurityException("Access denied. You can only update account types from your business unit.");
        }

        existingAccountType.setName(request.name());
        AccountType updatedAccountType = accountTypeJpaRepository.save(existingAccountType);
        return ResponseEntity.ok(new AccountTypeResponse(updatedAccountType));
    }

    /** DELETE /api/account-types/{id} - Deletar tipo de conta */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccountType(AuthToken authToken, @PathVariable Integer id) {

        AccountType accountType = accountTypeJpaRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("AccountType não encontrado"));

        // Verificar se o usuário tem acesso ao tipo de conta
        if (!authToken.isAdmin() && !accountType.getBusinessUnit().getId().equals(authToken.businessUnitId())) {
            throw new SecurityException("Access denied. You can only delete account types from your business unit.");
        }

        accountTypeJpaRepository.delete(accountType);
        return ResponseEntity.noContent().build();
    }
}
