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
@Table(name = "account_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_unit_id", nullable = false)
    private BusinessUnit businessUnit;

    public AccountType(String name, BusinessUnit businessUnit) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name is required.");
        }
        if (businessUnit == null) {
            throw new IllegalArgumentException("businessUnit is required.");
        }

        this.name = name;
        this.businessUnit = businessUnit;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof AccountType other && Objects.equals(this.getId(), other.getId());
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name is required.");
        }
        this.name = name;
    }

    public Long getBusinessUnitId() {
        return businessUnit != null ? businessUnit.getId() : null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
