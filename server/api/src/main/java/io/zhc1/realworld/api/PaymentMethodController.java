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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import io.zhc1.realworld.api.request.PaymentMethodRequest;
import io.zhc1.realworld.api.response.PaymentMethodResponse;
import io.zhc1.realworld.config.AuthToken;
import io.zhc1.realworld.model.PaymentMethod;
import io.zhc1.realworld.service.BusinessUnitService;
import io.zhc1.realworld.service.PaymentMethodService;

@RestController
@RequestMapping("/api/payment-methods")
@RequiredArgsConstructor
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;
    private final BusinessUnitService businessUnitService;

    /** GET /api/payment-methods - Listar todos os métodos de pagamento */
    @GetMapping
    public ResponseEntity<PaymentMethodResponse> getPaymentMethods(
            AuthToken authToken, @RequestParam(value = "businessUnit", required = false) Long businessUnitId) {

        // Se não especificar businessUnit, usa a do usuário logado
        if (businessUnitId == null) {
            businessUnitId = authToken.businessUnitId();
        }

        List<PaymentMethod> paymentMethods = paymentMethodService.getPaymentMethodsByBusinessUnit(
                businessUnitId, authToken.businessUnitId(), authToken.isAdmin());
        return ResponseEntity.ok(new PaymentMethodResponse(paymentMethods));
    }

    /** GET /api/payment-methods/{id} - Obter método de pagamento específico */
    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethodResponse> getPaymentMethod(AuthToken authToken, @PathVariable Integer id) {

        PaymentMethod paymentMethod =
                paymentMethodService.getPaymentMethod(id, authToken.businessUnitId(), authToken.isAdmin());

        return ResponseEntity.ok(new PaymentMethodResponse(paymentMethod));
    }

    /** POST /api/payment-methods - Criar novo método de pagamento */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PaymentMethodResponse> createPaymentMethod(
            AuthToken authToken, @RequestBody PaymentMethodRequest request) {

        // Se não especificar businessUnit, usa a do usuário logado
        Long businessUnitId = request.businessUnitId() != null ? request.businessUnitId() : authToken.businessUnitId();

        PaymentMethod savedPaymentMethod = paymentMethodService.createPaymentMethod(
                request.name(), request.description(), businessUnitId, authToken.businessUnitId(), authToken.isAdmin());
        return ResponseEntity.status(HttpStatus.CREATED).body(new PaymentMethodResponse(savedPaymentMethod));
    }

    /** PUT /api/payment-methods/{id} - Atualizar método de pagamento */
    @PutMapping("/{id}")
    public ResponseEntity<PaymentMethodResponse> updatePaymentMethod(
            AuthToken authToken, @PathVariable Integer id, @RequestBody PaymentMethodRequest request) {

        PaymentMethod updatedPaymentMethod = paymentMethodService.updatePaymentMethod(
                id, request.name(), request.description(), authToken.businessUnitId(), authToken.isAdmin());
        return ResponseEntity.ok(new PaymentMethodResponse(updatedPaymentMethod));
    }

    /** DELETE /api/payment-methods/{id} - Deletar método de pagamento */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentMethod(AuthToken authToken, @PathVariable Integer id) {

        paymentMethodService.deletePaymentMethod(id, authToken.businessUnitId(), authToken.isAdmin());
        return ResponseEntity.noContent().build();
    }

    /** PUT /api/payment-methods/{id}/activate - Ativar método de pagamento */
    @PutMapping("/{id}/activate")
    public ResponseEntity<PaymentMethodResponse> activatePaymentMethod(AuthToken authToken, @PathVariable Integer id) {

        PaymentMethod activatedPaymentMethod =
                paymentMethodService.activatePaymentMethod(id, authToken.businessUnitId(), authToken.isAdmin());
        return ResponseEntity.ok(new PaymentMethodResponse(activatedPaymentMethod));
    }

    /** PUT /api/payment-methods/{id}/deactivate - Desativar método de pagamento */
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<PaymentMethodResponse> deactivatePaymentMethod(
            AuthToken authToken, @PathVariable Integer id) {

        PaymentMethod deactivatedPaymentMethod =
                paymentMethodService.deactivatePaymentMethod(id, authToken.businessUnitId(), authToken.isAdmin());
        return ResponseEntity.ok(new PaymentMethodResponse(deactivatedPaymentMethod));
    }
}
