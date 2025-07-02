package io.zhc1.realworld.api;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import io.zhc1.realworld.api.request.CreateCashFlowRequest;
import io.zhc1.realworld.api.request.UpdateCashFlowRequest;
import io.zhc1.realworld.api.response.MultipleCashFlowsResponse;
import io.zhc1.realworld.api.response.SingleCashFlowResponse;
import io.zhc1.realworld.config.AuthToken;
import io.zhc1.realworld.model.AccountType;
import io.zhc1.realworld.model.BusinessUnit;
import io.zhc1.realworld.model.CashFlow;
import io.zhc1.realworld.model.Category;
import io.zhc1.realworld.model.PaymentMethod;
import io.zhc1.realworld.model.Profile;
import io.zhc1.realworld.model.TransactionType;
import io.zhc1.realworld.service.BusinessUnitService;
import io.zhc1.realworld.service.CashFlowService;
import io.zhc1.realworld.service.ProfileService;

@RestController
@RequiredArgsConstructor
class CashFlowController {
    private final CashFlowService cashFlowService;
    private final BusinessUnitService businessUnitService;
    private final ProfileService profileService;

    /** GET /api/cash-flows - Listar todos os fluxos de caixa de uma unidade de negócio */
    @GetMapping("/api/cash-flows")
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
    @GetMapping("/api/cash-flows/{id}")
    SingleCashFlowResponse getCashFlow(AuthToken authToken, @PathVariable Integer id) {
        CashFlow cashFlow = cashFlowService.getCashFlow(id, authToken.businessUnitId(), authToken.isAdmin());
        return new SingleCashFlowResponse(cashFlow);
    }

    /** POST /api/cash-flows - Criar um novo fluxo de caixa */
    @PostMapping("/api/cash-flows")
    @ResponseStatus(HttpStatus.CREATED)
    SingleCashFlowResponse createCashFlow(AuthToken authToken, @RequestBody CreateCashFlowRequest request) {

        // TODO: Implementar busca das entidades relacionadas por ID
        // Por enquanto, vamos usar valores mock para demonstração
        PaymentMethod paymentMethod = new PaymentMethod("PIX");
        TransactionType transactionType = new TransactionType("Income");

        // Se não especificar businessUnit, usa a do usuário logado
        Long businessUnitId = request.cashFlow().businessUnitId() != null
                ? request.cashFlow().businessUnitId()
                : authToken.businessUnitId();

        BusinessUnit businessUnit = businessUnitService
                .findById(businessUnitId)
                .orElseThrow(() -> new RuntimeException("BusinessUnit não encontrada"));

        Category category = new Category("Vendas", transactionType, businessUnit);
        AccountType accountType = new AccountType("BASA - Corrente", businessUnit);

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
    @PutMapping("/api/cash-flows/{id}")
    SingleCashFlowResponse updateCashFlow(
            AuthToken authToken, @PathVariable Integer id, @RequestBody UpdateCashFlowRequest request) {

        // TODO: Implementar busca das entidades relacionadas por ID
        PaymentMethod paymentMethod = new PaymentMethod("PIX");
        TransactionType transactionType = new TransactionType("Income");
        BusinessUnit businessUnit = new BusinessUnit("Default");
        Category category = new Category("Vendas", transactionType, businessUnit);
        AccountType accountType = new AccountType("BASA - Corrente", businessUnit);

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
    @DeleteMapping("/api/cash-flows/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCashFlow(AuthToken authToken, @PathVariable Integer id) {
        cashFlowService.deleteCashFlow(id, authToken.businessUnitId(), authToken.isAdmin());
    }

    /** GET /api/cash-flows/search - Buscar fluxos de caixa por período */
    @GetMapping("/api/cash-flows/search")
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
    @PutMapping("/api/cash-flows/{id}/check")
    SingleCashFlowResponse markAsChecked(AuthToken authToken, @PathVariable Integer id) {
        CashFlow cashFlow = cashFlowService.markAsChecked(id, authToken.businessUnitId(), authToken.isAdmin());
        return new SingleCashFlowResponse(cashFlow);
    }

    /** PUT /api/cash-flows/{id}/uncheck - Desmarcar como revisado */
    @PutMapping("/api/cash-flows/{id}/uncheck")
    SingleCashFlowResponse markAsUnchecked(AuthToken authToken, @PathVariable Integer id) {
        CashFlow cashFlow = cashFlowService.markAsUnchecked(id, authToken.businessUnitId(), authToken.isAdmin());
        return new SingleCashFlowResponse(cashFlow);
    }

    /** PUT /api/cash-flows/{id}/deactivate - Desativar fluxo de caixa */
    @PutMapping("/api/cash-flows/{id}/deactivate")
    SingleCashFlowResponse deactivate(AuthToken authToken, @PathVariable Integer id) {
        CashFlow cashFlow = cashFlowService.deactivate(id, authToken.businessUnitId(), authToken.isAdmin());
        return new SingleCashFlowResponse(cashFlow);
    }

    /** PUT /api/cash-flows/{id}/activate - Ativar fluxo de caixa */
    @PutMapping("/api/cash-flows/{id}/activate")
    SingleCashFlowResponse activate(AuthToken authToken, @PathVariable Integer id) {
        CashFlow cashFlow = cashFlowService.activate(id, authToken.businessUnitId(), authToken.isAdmin());
        return new SingleCashFlowResponse(cashFlow);
    }
}
