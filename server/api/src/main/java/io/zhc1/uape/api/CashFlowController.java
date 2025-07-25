package io.zhc1.uape.api;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import io.zhc1.uape.api.request.CreateCashFlowRequest;
import io.zhc1.uape.api.request.UpdateCashFlowRequest;
import io.zhc1.uape.api.response.MultipleCashFlowsResponse;
import io.zhc1.uape.api.response.SingleCashFlowResponse;
import io.zhc1.uape.config.AuthToken;
import io.zhc1.uape.model.AccountType;
import io.zhc1.uape.model.BusinessUnit;
import io.zhc1.uape.model.CashFlow;
import io.zhc1.uape.model.Category;
import io.zhc1.uape.model.PaymentMethod;
import io.zhc1.uape.model.Profile;
import io.zhc1.uape.model.TransactionType;
import io.zhc1.uape.persistence.AccountTypeJpaRepository;
import io.zhc1.uape.persistence.CategoryJpaRepository;
import io.zhc1.uape.persistence.PaymentMethodJpaRepository;
import io.zhc1.uape.persistence.TransactionTypeJpaRepository;
import io.zhc1.uape.service.BusinessUnitService;
import io.zhc1.uape.service.CashFlowService;
import io.zhc1.uape.service.ProfileService;

@RestController
@RequestMapping("/api/cash-flows")
@RequiredArgsConstructor
class CashFlowController {
    private final CashFlowService cashFlowService;
    private final BusinessUnitService businessUnitService;
    private final ProfileService profileService;
    private final PaymentMethodJpaRepository paymentMethodJpaRepository;
    private final TransactionTypeJpaRepository transactionTypeJpaRepository;
    private final CategoryJpaRepository categoryJpaRepository;
    private final AccountTypeJpaRepository accountTypeJpaRepository;

    /** GET /api/cash-flows - Listar todos os fluxos de caixa de uma unidade de negócio */
    @GetMapping
    MultipleCashFlowsResponse getCashFlows(
            AuthToken authToken, @RequestParam(value = "businessUnit", required = false) Long businessUnitId) {

        // Se não especificar businessUnit, usa a do usuário logado
        if (businessUnitId == null) {
            businessUnitId = authToken.businessUnitId();
        }

        BusinessUnit businessUnit = businessUnitService
                .findById(businessUnitId)
                .orElseThrow(() -> new RuntimeException("BusinessUnit não encontrada"));

        List<CashFlow> cashFlows = cashFlowService.getCashFlowsByBusinessUnit(
                businessUnit, authToken.businessUnitId(), authToken.isAdmin());

        return MultipleCashFlowsResponse.from(cashFlows);
    }

    /** GET /api/cash-flows/{id} - Obter um fluxo de caixa específico */
    @GetMapping("/{id}")
    SingleCashFlowResponse getCashFlow(AuthToken authToken, @PathVariable Integer id) {
        CashFlow cashFlow = cashFlowService.getCashFlow(id, authToken.businessUnitId(), authToken.isAdmin());
        return new SingleCashFlowResponse(cashFlow);
    }

    /** POST /api/cash-flows - Criar um novo fluxo de caixa */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    SingleCashFlowResponse createCashFlow(AuthToken authToken, @RequestBody CreateCashFlowRequest request) {

        // Buscar entidades relacionadas pelos IDs
        PaymentMethod paymentMethod = paymentMethodJpaRepository
                .findById(request.cashFlow().paymentMethodId())
                .orElseThrow(() -> new RuntimeException("PaymentMethod não encontrado"));

        TransactionType transactionType = transactionTypeJpaRepository
                .findById(request.cashFlow().transactionTypeId())
                .orElseThrow(() -> new RuntimeException("TransactionType não encontrado"));

        // Se não especificar businessUnit, usa a do usuário logado
        Long businessUnitId = request.cashFlow().businessUnitId() != null
                ? request.cashFlow().businessUnitId()
                : authToken.businessUnitId();

        BusinessUnit businessUnit = businessUnitService
                .findById(businessUnitId)
                .orElseThrow(() -> new RuntimeException("BusinessUnit não encontrada"));

        Category category = categoryJpaRepository
                .findById(request.cashFlow().categoryId())
                .orElseThrow(() -> new RuntimeException("Category não encontrada"));

        AccountType accountType = accountTypeJpaRepository
                .findById(request.cashFlow().accountTypeId())
                .orElseThrow(() -> new RuntimeException("AccountType não encontrado"));

        // Buscar profile se fornecido
        Profile profile = null;
        if (request.cashFlow().profileId() != null) {
            profile = profileService.findById(request.cashFlow().profileId()).orElse(null);
        }

        CashFlow cashFlow = cashFlowService.createCashFlow(
                request.cashFlow().amount(),
                request.cashFlow().transactionDate(),
                request.cashFlow().description(),
                paymentMethod,
                transactionType,
                category,
                accountType,
                businessUnit,
                profile,
                authToken.userId().toString(),
                authToken.businessUnitId(),
                authToken.isAdmin());

        return new SingleCashFlowResponse(cashFlow);
    }

    /** PUT /api/cash-flows/{id} - Atualizar um fluxo de caixa */
    @PutMapping("/{id}")
    SingleCashFlowResponse updateCashFlow(
            AuthToken authToken, @PathVariable Integer id, @RequestBody UpdateCashFlowRequest request) {

        // Buscar entidades relacionadas pelos IDs
        PaymentMethod paymentMethod = paymentMethodJpaRepository
                .findById(request.cashFlow().paymentMethodId())
                .orElseThrow(() -> new RuntimeException("PaymentMethod não encontrado"));

        TransactionType transactionType = transactionTypeJpaRepository
                .findById(request.cashFlow().transactionTypeId())
                .orElseThrow(() -> new RuntimeException("TransactionType não encontrado"));

        Category category = categoryJpaRepository
                .findById(request.cashFlow().categoryId())
                .orElseThrow(() -> new RuntimeException("Category não encontrada"));

        AccountType accountType = accountTypeJpaRepository
                .findById(request.cashFlow().accountTypeId())
                .orElseThrow(() -> new RuntimeException("AccountType não encontrado"));

        // Buscar profile se fornecido
        Profile profile = null;
        if (request.cashFlow().profileId() != null) {
            profile = profileService.findById(request.cashFlow().profileId()).orElse(null);
        }

        CashFlow cashFlow = cashFlowService.updateCashFlow(
                id,
                request.cashFlow().amount(),
                request.cashFlow().transactionDate(),
                request.cashFlow().description(),
                paymentMethod,
                transactionType,
                category,
                accountType,
                profile,
                authToken.userId().toString(),
                authToken.businessUnitId(),
                authToken.isAdmin());

        return new SingleCashFlowResponse(cashFlow);
    }

    /** DELETE /api/cash-flows/{id} - Deletar um fluxo de caixa */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCashFlow(AuthToken authToken, @PathVariable Integer id) {
        cashFlowService.deleteCashFlow(id, authToken.businessUnitId(), authToken.isAdmin());
    }

    /** GET /api/cash-flows/search - Buscar fluxos de caixa por período */
    @GetMapping("/search")
    MultipleCashFlowsResponse searchCashFlows(
            AuthToken authToken,
            @RequestParam(value = "businessUnit", required = false) Long businessUnitId,
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {

        // Se não especificar businessUnit, usa a do usuário logado
        if (businessUnitId == null) {
            businessUnitId = authToken.businessUnitId();
        }

        BusinessUnit businessUnit = businessUnitService
                .findById(businessUnitId)
                .orElseThrow(() -> new RuntimeException("BusinessUnit não encontrada"));

        List<CashFlow> cashFlows = cashFlowService.getCashFlowsByDateRange(
                businessUnit, startDate, endDate, authToken.businessUnitId(), authToken.isAdmin());

        return MultipleCashFlowsResponse.from(cashFlows);
    }

    /** PUT /api/cash-flows/{id}/check - Marcar como revisado */
    @PutMapping("/{id}/check")
    SingleCashFlowResponse markAsChecked(AuthToken authToken, @PathVariable Integer id) {
        CashFlow cashFlow = cashFlowService.markAsChecked(id, authToken.businessUnitId(), authToken.isAdmin());
        return new SingleCashFlowResponse(cashFlow);
    }

    /** PUT /api/cash-flows/{id}/uncheck - Desmarcar como revisado */
    @PutMapping("/{id}/uncheck")
    SingleCashFlowResponse markAsUnchecked(AuthToken authToken, @PathVariable Integer id) {
        CashFlow cashFlow = cashFlowService.markAsUnchecked(id, authToken.businessUnitId(), authToken.isAdmin());
        return new SingleCashFlowResponse(cashFlow);
    }

    /** PUT /api/cash-flows/{id}/deactivate - Desativar fluxo de caixa */
    @PutMapping("/{id}/deactivate")
    SingleCashFlowResponse deactivate(AuthToken authToken, @PathVariable Integer id) {
        CashFlow cashFlow = cashFlowService.deactivate(id, authToken.businessUnitId(), authToken.isAdmin());
        return new SingleCashFlowResponse(cashFlow);
    }

    /** PUT /api/cash-flows/{id}/activate - Ativar fluxo de caixa */
    @PutMapping("/{id}/activate")
    SingleCashFlowResponse activate(AuthToken authToken, @PathVariable Integer id) {
        CashFlow cashFlow = cashFlowService.activate(id, authToken.businessUnitId(), authToken.isAdmin());
        return new SingleCashFlowResponse(cashFlow);
    }
}
