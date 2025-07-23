package io.zhc1.uape.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import io.zhc1.uape.model.AccountType;
import io.zhc1.uape.model.BusinessUnit;
import io.zhc1.uape.model.CashFlow;
import io.zhc1.uape.model.CashFlowRepository;
import io.zhc1.uape.model.Category;
import io.zhc1.uape.model.PaymentMethod;
import io.zhc1.uape.model.Profile;
import io.zhc1.uape.model.TransactionType;

@Service
@RequiredArgsConstructor
public class CashFlowService {
    private final CashFlowRepository cashFlowRepository;

    /**
     * Get cash flow by id with access control.
     *
     * @param id cash flow id
     * @param userBusinessUnitId user's business unit id
     * @param isAdmin if user is admin
     * @return Returns cash flow
     */
    public CashFlow getCashFlow(Integer id, Long userBusinessUnitId, boolean isAdmin) {
        CashFlow cashFlow =
                cashFlowRepository.findById(id).orElseThrow(() -> new NoSuchElementException("cash flow not found."));

        // Admin can access any cash flow, USER only from their business unit
        if (!isAdmin && !cashFlow.getBusinessUnit().getId().equals(userBusinessUnitId)) {
            throw new SecurityException("Access denied. You can only access cash flows from your business unit.");
        }

        return cashFlow;
    }

    /**
     * Get cash flow by id.
     *
     * @param id cash flow id
     * @return Returns cash flow
     */
    public CashFlow getCashFlow(Integer id) {
        return cashFlowRepository.findById(id).orElseThrow(() -> new NoSuchElementException("cash flow not found."));
    }

    /**
     * Get all cash flows by business unit with access control.
     *
     * @param businessUnit business unit
     * @param userBusinessUnitId user's business unit id
     * @param isAdmin if user is admin
     * @return Returns list of cash flows
     */
    public List<CashFlow> getCashFlowsByBusinessUnit(
            BusinessUnit businessUnit, Long userBusinessUnitId, boolean isAdmin) {
        if (businessUnit == null) {
            throw new IllegalArgumentException("business unit is required.");
        }

        // Admin can access any business unit, USER only their own
        if (!isAdmin && !businessUnit.getId().equals(userBusinessUnitId)) {
            throw new SecurityException("Access denied. You can only access cash flows from your business unit.");
        }

        return cashFlowRepository.findByBusinessUnit(businessUnit);
    }

    /**
     * Get all cash flows by business unit.
     *
     * @param businessUnit business unit
     * @return Returns list of cash flows
     */
    public List<CashFlow> getCashFlowsByBusinessUnit(BusinessUnit businessUnit) {
        if (businessUnit == null) {
            throw new IllegalArgumentException("business unit is required.");
        }
        return cashFlowRepository.findByBusinessUnit(businessUnit);
    }

    /**
     * Get cash flows by business unit and date range with access control.
     *
     * @param businessUnit business unit
     * @param startDate start date
     * @param endDate end date
     * @param userBusinessUnitId user's business unit id
     * @param isAdmin if user is admin
     * @return Returns list of cash flows
     */
    public List<CashFlow> getCashFlowsByDateRange(
            BusinessUnit businessUnit,
            LocalDate startDate,
            LocalDate endDate,
            Long userBusinessUnitId,
            boolean isAdmin) {
        if (businessUnit == null) {
            throw new IllegalArgumentException("business unit is required.");
        }
        if (startDate == null) {
            throw new IllegalArgumentException("start date is required.");
        }
        if (endDate == null) {
            throw new IllegalArgumentException("end date is required.");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("start date cannot be after end date.");
        }

        // Admin can access any business unit, USER only their own
        if (!isAdmin && !businessUnit.getId().equals(userBusinessUnitId)) {
            throw new SecurityException("Access denied. You can only access cash flows from your business unit.");
        }

        return cashFlowRepository.findByBusinessUnitAndDateRange(businessUnit, startDate, endDate);
    }

    /**
     * Get cash flows by business unit and date range.
     *
     * @param businessUnit business unit
     * @param startDate start date
     * @param endDate end date
     * @return Returns list of cash flows
     */
    public List<CashFlow> getCashFlowsByDateRange(BusinessUnit businessUnit, LocalDate startDate, LocalDate endDate) {
        if (businessUnit == null) {
            throw new IllegalArgumentException("business unit is required.");
        }
        if (startDate == null) {
            throw new IllegalArgumentException("start date is required.");
        }
        if (endDate == null) {
            throw new IllegalArgumentException("end date is required.");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("start date cannot be after end date.");
        }

        return cashFlowRepository.findByBusinessUnitAndDateRange(businessUnit, startDate, endDate);
    }

    /**
     * Create a new cash flow with access control.
     *
     * @param amount transaction amount
     * @param transactionDate transaction date
     * @param description transaction description
     * @param paymentMethod payment method
     * @param transactionType transaction type
     * @param category category
     * @param accountType account type
     * @param businessUnit business unit
     * @param createdBy user who created
     * @param userBusinessUnitId user's business unit id
     * @param isAdmin if user is admin
     * @return Returns the created cash flow
     */
    public CashFlow createCashFlow(
            BigDecimal amount,
            LocalDate transactionDate,
            String description,
            PaymentMethod paymentMethod,
            TransactionType transactionType,
            Category category,
            AccountType accountType,
            BusinessUnit businessUnit,
            Profile profile,
            String createdBy,
            Long userBusinessUnitId,
            boolean isAdmin) {

        // Admin can create in any business unit, USER only in their own
        if (!isAdmin && !businessUnit.getId().equals(userBusinessUnitId)) {
            throw new SecurityException("Access denied. You can only create cash flows in your business unit.");
        }

        var cashFlow = new CashFlow(
                amount,
                transactionDate,
                description,
                paymentMethod,
                transactionType,
                category,
                accountType,
                businessUnit,
                profile,
                createdBy);

        return cashFlowRepository.save(cashFlow);
    }

    /**
     * Create a new cash flow.
     *
     * @param amount transaction amount
     * @param transactionDate transaction date
     * @param description transaction description
     * @param paymentMethod payment method
     * @param transactionType transaction type
     * @param category category
     * @param accountType account type
     * @param businessUnit business unit
     * @param createdBy user who created
     * @return Returns the created cash flow
     */
    public CashFlow createCashFlow(
            BigDecimal amount,
            LocalDate transactionDate,
            String description,
            PaymentMethod paymentMethod,
            TransactionType transactionType,
            Category category,
            AccountType accountType,
            BusinessUnit businessUnit,
            Profile profile,
            String createdBy) {

        var cashFlow = new CashFlow(
                amount,
                transactionDate,
                description,
                paymentMethod,
                transactionType,
                category,
                accountType,
                businessUnit,
                profile,
                createdBy);

        return cashFlowRepository.save(cashFlow);
    }

    /**
     * Update cash flow with access control.
     *
     * @param id cash flow id
     * @param amount transaction amount
     * @param transactionDate transaction date
     * @param description transaction description
     * @param paymentMethod payment method
     * @param transactionType transaction type
     * @param category category
     * @param accountType account type
     * @param updatedBy user who updated
     * @param userBusinessUnitId user's business unit id
     * @param isAdmin if user is admin
     * @return Returns the updated cash flow
     */
    public CashFlow updateCashFlow(
            Integer id,
            BigDecimal amount,
            LocalDate transactionDate,
            String description,
            PaymentMethod paymentMethod,
            TransactionType transactionType,
            Category category,
            AccountType accountType,
            Profile profile,
            String updatedBy,
            Long userBusinessUnitId,
            boolean isAdmin) {

        var cashFlow = getCashFlow(id, userBusinessUnitId, isAdmin);
        cashFlow.update(
                amount,
                transactionDate,
                description,
                paymentMethod,
                transactionType,
                category,
                accountType,
                profile,
                updatedBy);

        return cashFlowRepository.save(cashFlow);
    }

    /**
     * Update cash flow.
     *
     * @param id cash flow id
     * @param amount transaction amount
     * @param transactionDate transaction date
     * @param description transaction description
     * @param paymentMethod payment method
     * @param transactionType transaction type
     * @param category category
     * @param accountType account type
     * @param updatedBy user who updated
     * @return Returns the updated cash flow
     */
    public CashFlow updateCashFlow(
            Integer id,
            BigDecimal amount,
            LocalDate transactionDate,
            String description,
            PaymentMethod paymentMethod,
            TransactionType transactionType,
            Category category,
            AccountType accountType,
            Profile profile,
            String updatedBy) {

        var cashFlow = getCashFlow(id);
        cashFlow.update(
                amount,
                transactionDate,
                description,
                paymentMethod,
                transactionType,
                category,
                accountType,
                profile,
                updatedBy);

        return cashFlowRepository.save(cashFlow);
    }

    /**
     * Delete cash flow with access control.
     *
     * @param id cash flow id
     * @param userBusinessUnitId user's business unit id
     * @param isAdmin if user is admin
     */
    public void deleteCashFlow(Integer id, Long userBusinessUnitId, boolean isAdmin) {
        var cashFlow = getCashFlow(id, userBusinessUnitId, isAdmin);
        cashFlowRepository.delete(cashFlow);
    }

    /**
     * Delete cash flow.
     *
     * @param id cash flow id
     */
    public void deleteCashFlow(Integer id) {
        var cashFlow = getCashFlow(id);
        cashFlowRepository.delete(cashFlow);
    }

    /**
     * Mark cash flow as checked with access control.
     *
     * @param id cash flow id
     * @param userBusinessUnitId user's business unit id
     * @param isAdmin if user is admin
     * @return Returns the updated cash flow
     */
    public CashFlow markAsChecked(Integer id, Long userBusinessUnitId, boolean isAdmin) {
        var cashFlow = getCashFlow(id, userBusinessUnitId, isAdmin);
        cashFlow.setChecked(true);
        return cashFlowRepository.save(cashFlow);
    }

    /**
     * Mark cash flow as checked.
     *
     * @param id cash flow id
     * @return Returns the updated cash flow
     */
    public CashFlow markAsChecked(Integer id) {
        var cashFlow = getCashFlow(id);
        cashFlow.setChecked(true);
        return cashFlowRepository.save(cashFlow);
    }

    /**
     * Mark cash flow as unchecked with access control.
     *
     * @param id cash flow id
     * @param userBusinessUnitId user's business unit id
     * @param isAdmin if user is admin
     * @return Returns the updated cash flow
     */
    public CashFlow markAsUnchecked(Integer id, Long userBusinessUnitId, boolean isAdmin) {
        var cashFlow = getCashFlow(id, userBusinessUnitId, isAdmin);
        cashFlow.setChecked(false);
        return cashFlowRepository.save(cashFlow);
    }

    /**
     * Mark cash flow as unchecked.
     *
     * @param id cash flow id
     * @return Returns the updated cash flow
     */
    public CashFlow markAsUnchecked(Integer id) {
        var cashFlow = getCashFlow(id);
        cashFlow.setChecked(false);
        return cashFlowRepository.save(cashFlow);
    }

    /**
     * Deactivate cash flow with access control.
     *
     * @param id cash flow id
     * @param userBusinessUnitId user's business unit id
     * @param isAdmin if user is admin
     * @return Returns the updated cash flow
     */
    public CashFlow deactivate(Integer id, Long userBusinessUnitId, boolean isAdmin) {
        var cashFlow = getCashFlow(id, userBusinessUnitId, isAdmin);
        cashFlow.setActive(false);
        return cashFlowRepository.save(cashFlow);
    }

    /**
     * Deactivate cash flow.
     *
     * @param id cash flow id
     * @return Returns the updated cash flow
     */
    public CashFlow deactivate(Integer id) {
        var cashFlow = getCashFlow(id);
        cashFlow.setActive(false);
        return cashFlowRepository.save(cashFlow);
    }

    /**
     * Activate cash flow with access control.
     *
     * @param id cash flow id
     * @param userBusinessUnitId user's business unit id
     * @param isAdmin if user is admin
     * @return Returns the updated cash flow
     */
    public CashFlow activate(Integer id, Long userBusinessUnitId, boolean isAdmin) {
        var cashFlow = getCashFlow(id, userBusinessUnitId, isAdmin);
        cashFlow.setActive(true);
        return cashFlowRepository.save(cashFlow);
    }

    /**
     * Activate cash flow.
     *
     * @param id cash flow id
     * @return Returns the updated cash flow
     */
    public CashFlow activate(Integer id) {
        var cashFlow = getCashFlow(id);
        cashFlow.setActive(true);
        return cashFlowRepository.save(cashFlow);
    }
}
