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
import io.zhc1.realworld.model.BusinessUnit;
import io.zhc1.realworld.model.PaymentMethod;
import io.zhc1.realworld.persistence.PaymentMethodJpaRepository;
import io.zhc1.realworld.service.BusinessUnitService;

@RestController
@RequestMapping("/api/payment-methods")
@RequiredArgsConstructor
public class PaymentMethodController {

    private final PaymentMethodJpaRepository paymentMethodJpaRepository;
    private final BusinessUnitService businessUnitService;

    /** GET /api/payment-methods - Listar todos os métodos de pagamento */
    @GetMapping
    public ResponseEntity<PaymentMethodResponse> getPaymentMethods(
            AuthToken authToken, @RequestParam(value = "businessUnit", required = false) Long businessUnitId) {

        // Se não especificar businessUnit, usa a do usuário logado
        if (businessUnitId == null) {
            businessUnitId = authToken.businessUnitId();
        }

        // Verificar se o usuário tem acesso à unidade de negócio
        if (!authToken.isAdmin() && !businessUnitId.equals(authToken.businessUnitId())) {
            throw new SecurityException("Access denied. You can only access payment methods from your business unit.");
        }

        List<PaymentMethod> paymentMethods = paymentMethodJpaRepository.findByBusinessUnit_Id(businessUnitId);
        return ResponseEntity.ok(new PaymentMethodResponse(paymentMethods));
    }

    /** GET /api/payment-methods/{id} - Obter método de pagamento específico */
    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethodResponse> getPaymentMethod(AuthToken authToken, @PathVariable Integer id) {

        PaymentMethod paymentMethod = paymentMethodJpaRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("PaymentMethod não encontrado"));

        // Verificar se o usuário tem acesso ao método de pagamento
        if (!authToken.isAdmin() && !paymentMethod.getBusinessUnitId().equals(authToken.businessUnitId())) {
            throw new SecurityException("Access denied. You can only access payment methods from your business unit.");
        }

        return ResponseEntity.ok(new PaymentMethodResponse(paymentMethod));
    }

    /** POST /api/payment-methods - Criar novo método de pagamento */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PaymentMethodResponse> createPaymentMethod(
            AuthToken authToken, @RequestBody PaymentMethodRequest request) {

        // Se não especificar businessUnit, usa a do usuário logado
        Long businessUnitId = request.businessUnitId() != null ? request.businessUnitId() : authToken.businessUnitId();

        // Verificar se o usuário tem acesso à unidade de negócio
        if (!authToken.isAdmin() && !businessUnitId.equals(authToken.businessUnitId())) {
            throw new SecurityException("Access denied. You can only create payment methods for your business unit.");
        }

        // Verificar se a unidade de negócio existe
        BusinessUnit businessUnit = businessUnitService
                .findById(businessUnitId)
                .orElseThrow(() -> new RuntimeException("BusinessUnit não encontrada"));

        PaymentMethod paymentMethod = new PaymentMethod(request.name(), request.description(), businessUnit);

        PaymentMethod savedPaymentMethod = paymentMethodJpaRepository.save(paymentMethod);
        return ResponseEntity.status(HttpStatus.CREATED).body(new PaymentMethodResponse(savedPaymentMethod));
    }

    /** PUT /api/payment-methods/{id} - Atualizar método de pagamento */
    @PutMapping("/{id}")
    public ResponseEntity<PaymentMethodResponse> updatePaymentMethod(
            AuthToken authToken, @PathVariable Integer id, @RequestBody PaymentMethodRequest request) {

        PaymentMethod existingPaymentMethod = paymentMethodJpaRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("PaymentMethod não encontrado"));

        // Verificar se o usuário tem acesso ao método de pagamento
        if (!authToken.isAdmin() && !existingPaymentMethod.getBusinessUnitId().equals(authToken.businessUnitId())) {
            throw new SecurityException("Access denied. You can only update payment methods from your business unit.");
        }

        existingPaymentMethod.setName(request.name());
        existingPaymentMethod.setDescription(request.description());

        PaymentMethod updatedPaymentMethod = paymentMethodJpaRepository.save(existingPaymentMethod);
        return ResponseEntity.ok(new PaymentMethodResponse(updatedPaymentMethod));
    }

    /** DELETE /api/payment-methods/{id} - Deletar método de pagamento */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentMethod(AuthToken authToken, @PathVariable Integer id) {

        PaymentMethod paymentMethod = paymentMethodJpaRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("PaymentMethod não encontrado"));

        // Verificar se o usuário tem acesso ao método de pagamento
        if (!authToken.isAdmin() && !paymentMethod.getBusinessUnitId().equals(authToken.businessUnitId())) {
            throw new SecurityException("Access denied. You can only delete payment methods from your business unit.");
        }

        paymentMethodJpaRepository.delete(paymentMethod);
        return ResponseEntity.noContent().build();
    }

    /** PUT /api/payment-methods/{id}/activate - Ativar método de pagamento */
    @PutMapping("/{id}/activate")
    public ResponseEntity<PaymentMethodResponse> activatePaymentMethod(AuthToken authToken, @PathVariable Integer id) {

        PaymentMethod paymentMethod = paymentMethodJpaRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("PaymentMethod não encontrado"));

        // Verificar se o usuário tem acesso ao método de pagamento
        if (!authToken.isAdmin() && !paymentMethod.getBusinessUnitId().equals(authToken.businessUnitId())) {
            throw new SecurityException(
                    "Access denied. You can only activate payment methods from your business unit.");
        }

        paymentMethod.setActive(true);
        PaymentMethod activatedPaymentMethod = paymentMethodJpaRepository.save(paymentMethod);
        return ResponseEntity.ok(new PaymentMethodResponse(activatedPaymentMethod));
    }

    /** PUT /api/payment-methods/{id}/deactivate - Desativar método de pagamento */
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<PaymentMethodResponse> deactivatePaymentMethod(
            AuthToken authToken, @PathVariable Integer id) {

        PaymentMethod paymentMethod = paymentMethodJpaRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("PaymentMethod não encontrado"));

        // Verificar se o usuário tem acesso ao método de pagamento
        if (!authToken.isAdmin() && !paymentMethod.getBusinessUnitId().equals(authToken.businessUnitId())) {
            throw new SecurityException(
                    "Access denied. You can only deactivate payment methods from your business unit.");
        }

        paymentMethod.setActive(false);
        PaymentMethod deactivatedPaymentMethod = paymentMethodJpaRepository.save(paymentMethod);
        return ResponseEntity.ok(new PaymentMethodResponse(deactivatedPaymentMethod));
    }
}
