package io.zhc1.uape.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import io.zhc1.uape.model.BusinessUnit;

interface BusinessUnitJpaRepository extends JpaRepository<BusinessUnit, Long> {}
