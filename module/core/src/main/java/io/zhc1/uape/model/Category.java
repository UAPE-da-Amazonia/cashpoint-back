package io.zhc1.uape.model;

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
@Table(name = "category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transaction_type_id", nullable = false)
    private TransactionType transactionType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "business_unit_id", nullable = false)
    private BusinessUnit businessUnit;

    public Category(String name, TransactionType transactionType, BusinessUnit businessUnit) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name is required.");
        }
        if (transactionType == null) {
            throw new IllegalArgumentException("transactionType is required.");
        }
        if (businessUnit == null) {
            throw new IllegalArgumentException("businessUnit is required.");
        }

        this.name = name;
        this.transactionType = transactionType;
        this.businessUnit = businessUnit;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Category other && Objects.equals(this.getId(), other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    public Integer getTransactionTypeId() {
        return transactionType != null ? transactionType.getId() : null;
    }

    public Long getBusinessUnitId() {
        return businessUnit != null ? businessUnit.getId() : null;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name is required.");
        }
        this.name = name;
    }
}
