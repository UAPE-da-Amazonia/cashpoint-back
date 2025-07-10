package io.zhc1.realworld.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "cash_flow")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CashFlow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(precision = 16, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate transactionDate;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id", nullable = false)
    private PaymentMethod paymentMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_type_id", nullable = false)
    private TransactionType transactionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_type_id", nullable = false)
    private AccountType accountType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_unit_id", nullable = false)
    private BusinessUnit businessUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Column(nullable = false, updatable = false)
    private final LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    @Column(length = 45)
    private String createdBy;

    @Column(length = 45)
    private String updatedBy;

    @Column(nullable = false)
    private boolean isActive = true;

    @Column(nullable = false)
    private boolean isChecked = false;

    public CashFlow(
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

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount must be positive.");
        }
        if (transactionDate == null) {
            throw new IllegalArgumentException("transactionDate is required.");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("description is required.");
        }
        if (paymentMethod == null) {
            throw new IllegalArgumentException("paymentMethod is required.");
        }
        if (transactionType == null) {
            throw new IllegalArgumentException("transactionType is required.");
        }
        if (category == null) {
            throw new IllegalArgumentException("category is required.");
        }
        if (accountType == null) {
            throw new IllegalArgumentException("accountType is required.");
        }
        if (businessUnit == null) {
            throw new IllegalArgumentException("businessUnit is required.");
        }
        if (createdBy == null || createdBy.isBlank()) {
            throw new IllegalArgumentException("createdBy is required.");
        }

        this.amount = amount;
        this.transactionDate = transactionDate;
        this.description = description;
        this.paymentMethod = paymentMethod;
        this.transactionType = transactionType;
        this.category = category;
        this.accountType = accountType;
        this.businessUnit = businessUnit;
        this.profile = profile;
        this.createdBy = createdBy;
    }

    public void update(
            BigDecimal amount,
            LocalDate transactionDate,
            String description,
            PaymentMethod paymentMethod,
            TransactionType transactionType,
            Category category,
            AccountType accountType,
            Profile profile,
            String updatedBy) {

        if (amount != null && amount.compareTo(BigDecimal.ZERO) > 0) {
            this.amount = amount;
        }
        if (transactionDate != null) {
            this.transactionDate = transactionDate;
        }
        if (description != null && !description.isBlank()) {
            this.description = description;
        }
        if (paymentMethod != null) {
            this.paymentMethod = paymentMethod;
        }
        if (transactionType != null) {
            this.transactionType = transactionType;
        }
        if (category != null) {
            this.category = category;
        }
        if (accountType != null) {
            this.accountType = accountType;
        }
        if (profile != null) {
            this.profile = profile;
        }
        if (updatedBy != null && !updatedBy.isBlank()) {
            this.updatedBy = updatedBy;
        }

        this.updatedAt = LocalDateTime.now();
    }

    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof CashFlow other && Objects.equals(this.getId(), other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    public Integer getPaymentMethodId() {
        return paymentMethod != null ? paymentMethod.getId() : null;
    }

    public Integer getTransactionTypeId() {
        return transactionType != null ? transactionType.getId() : null;
    }

    public Integer getCategoryId() {
        return category != null ? category.getId() : null;
    }

    public Integer getAccountTypeId() {
        return accountType != null ? accountType.getId() : null;
    }

    public Long getBusinessUnitId() {
        return businessUnit != null ? businessUnit.getId() : null;
    }

    public Integer getProfileId() {
        return profile != null ? profile.getId() : null;
    }
}
