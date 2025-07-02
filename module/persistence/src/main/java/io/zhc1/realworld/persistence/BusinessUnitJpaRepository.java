package io.zhc1.realworld.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import io.zhc1.realworld.model.BusinessUnit;

interface BusinessUnitJpaRepository extends JpaRepository<BusinessUnit, Long> {}
