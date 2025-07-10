package io.zhc1.realworld.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import io.zhc1.realworld.model.BusinessUnit;
import io.zhc1.realworld.model.PaymentMethod;
import io.zhc1.realworld.model.PaymentMethodRepository;

@Service
@RequiredArgsConstructor
public class PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;
    private final BusinessUnitService businessUnitService;

    /**
     * Get payment method by id with access control.
     *
     * @param id payment method id
     * @param userBusinessUnitId user's business unit id
     * @param isAdmin if user is admin
     * @return Returns payment method
     */
    public PaymentMethod getPaymentMethod(Integer id, Long userBusinessUnitId, boolean isAdmin) {
        PaymentMethod paymentMethod = paymentMethodRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("payment method not found."));

        // Admin can access any payment method, USER only from their business unit
        if (!isAdmin && !paymentMethod.getBusinessUnitId().equals(userBusinessUnitId)) {
            throw new SecurityException("Access denied. You can only access payment methods from your business unit.");
        }

        return paymentMethod;
    }

    /**
     * Get payment method by id.
     *
     * @param id payment method id
     * @return Returns payment method
     */
    public PaymentMethod getPaymentMethod(Integer id) {
        return paymentMethodRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("payment method not found."));
    }

    /**
     * Get all payment methods by business unit with access control.
     *
     * @param businessUnitId business unit id
     * @param userBusinessUnitId user's business unit id
     * @param isAdmin if user is admin
     * @return Returns list of payment methods
     */
    public List<PaymentMethod> getPaymentMethodsByBusinessUnit(
            Long businessUnitId, Long userBusinessUnitId, boolean isAdmin) {
        // Admin can access any business unit, USER only their own
        if (!isAdmin && !businessUnitId.equals(userBusinessUnitId)) {
            throw new SecurityException("Access denied. You can only access payment methods from your business unit.");
        }

        return paymentMethodRepository.findByBusinessUnitId(businessUnitId);
    }

    /**
     * Get all payment methods by business unit.
     *
     * @param businessUnitId business unit id
     * @return Returns list of payment methods
     */
    public List<PaymentMethod> getPaymentMethodsByBusinessUnit(Long businessUnitId) {
        return paymentMethodRepository.findByBusinessUnitId(businessUnitId);
    }

    /**
     * Create a new payment method with access control.
     *
     * @param name payment method name
     * @param description payment method description
     * @param businessUnitId business unit id
     * @param userBusinessUnitId user's business unit id
     * @param isAdmin if user is admin
     * @return Returns the created payment method
     */
    public PaymentMethod createPaymentMethod(
            String name, String description, Long businessUnitId, Long userBusinessUnitId, boolean isAdmin) {
        // Admin can create in any business unit, USER only in their own
        if (!isAdmin && !businessUnitId.equals(userBusinessUnitId)) {
            throw new SecurityException("Access denied. You can only create payment methods in your business unit.");
        }

        BusinessUnit businessUnit = businessUnitService
                .findById(businessUnitId)
                .orElseThrow(() -> new IllegalArgumentException("business unit not found."));

        var paymentMethod = new PaymentMethod(name, description, businessUnit);
        return paymentMethodRepository.save(paymentMethod);
    }

    /**
     * Create a new payment method.
     *
     * @param name payment method name
     * @param description payment method description
     * @param businessUnitId business unit id
     * @return Returns the created payment method
     */
    public PaymentMethod createPaymentMethod(String name, String description, Long businessUnitId) {
        BusinessUnit businessUnit = businessUnitService
                .findById(businessUnitId)
                .orElseThrow(() -> new IllegalArgumentException("business unit not found."));

        var paymentMethod = new PaymentMethod(name, description, businessUnit);
        return paymentMethodRepository.save(paymentMethod);
    }

    /**
     * Update a payment method with access control.
     *
     * @param id payment method id
     * @param name payment method name
     * @param description payment method description
     * @param userBusinessUnitId user's business unit id
     * @param isAdmin if user is admin
     * @return Returns the updated payment method
     */
    public PaymentMethod updatePaymentMethod(
            Integer id, String name, String description, Long userBusinessUnitId, boolean isAdmin) {
        PaymentMethod paymentMethod = getPaymentMethod(id, userBusinessUnitId, isAdmin);
        paymentMethod.setName(name);
        paymentMethod.setDescription(description);
        return paymentMethodRepository.save(paymentMethod);
    }

    /**
     * Update a payment method.
     *
     * @param id payment method id
     * @param name payment method name
     * @param description payment method description
     * @return Returns the updated payment method
     */
    public PaymentMethod updatePaymentMethod(Integer id, String name, String description) {
        PaymentMethod paymentMethod = getPaymentMethod(id);
        paymentMethod.setName(name);
        paymentMethod.setDescription(description);
        return paymentMethodRepository.save(paymentMethod);
    }

    /**
     * Delete a payment method with access control.
     *
     * @param id payment method id
     * @param userBusinessUnitId user's business unit id
     * @param isAdmin if user is admin
     */
    public void deletePaymentMethod(Integer id, Long userBusinessUnitId, boolean isAdmin) {
        PaymentMethod paymentMethod = getPaymentMethod(id, userBusinessUnitId, isAdmin);
        paymentMethodRepository.delete(paymentMethod);
    }

    /**
     * Delete a payment method.
     *
     * @param id payment method id
     */
    public void deletePaymentMethod(Integer id) {
        PaymentMethod paymentMethod = getPaymentMethod(id);
        paymentMethodRepository.delete(paymentMethod);
    }

    /**
     * Activate a payment method with access control.
     *
     * @param id payment method id
     * @param userBusinessUnitId user's business unit id
     * @param isAdmin if user is admin
     * @return Returns the activated payment method
     */
    public PaymentMethod activatePaymentMethod(Integer id, Long userBusinessUnitId, boolean isAdmin) {
        PaymentMethod paymentMethod = getPaymentMethod(id, userBusinessUnitId, isAdmin);
        paymentMethod.setActive(true);
        return paymentMethodRepository.save(paymentMethod);
    }

    /**
     * Activate a payment method.
     *
     * @param id payment method id
     * @return Returns the activated payment method
     */
    public PaymentMethod activatePaymentMethod(Integer id) {
        PaymentMethod paymentMethod = getPaymentMethod(id);
        paymentMethod.setActive(true);
        return paymentMethodRepository.save(paymentMethod);
    }

    /**
     * Deactivate a payment method with access control.
     *
     * @param id payment method id
     * @param userBusinessUnitId user's business unit id
     * @param isAdmin if user is admin
     * @return Returns the deactivated payment method
     */
    public PaymentMethod deactivatePaymentMethod(Integer id, Long userBusinessUnitId, boolean isAdmin) {
        PaymentMethod paymentMethod = getPaymentMethod(id, userBusinessUnitId, isAdmin);
        paymentMethod.setActive(false);
        return paymentMethodRepository.save(paymentMethod);
    }

    /**
     * Deactivate a payment method.
     *
     * @param id payment method id
     * @return Returns the deactivated payment method
     */
    public PaymentMethod deactivatePaymentMethod(Integer id) {
        PaymentMethod paymentMethod = getPaymentMethod(id);
        paymentMethod.setActive(false);
        return paymentMethodRepository.save(paymentMethod);
    }
}
