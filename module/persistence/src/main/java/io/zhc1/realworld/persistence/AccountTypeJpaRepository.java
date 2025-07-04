package io.zhc1.realworld.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.zhc1.realworld.model.AccountType;

public interface AccountTypeJpaRepository extends JpaRepository<AccountType, Integer> {

    List<AccountType> findByBusinessUnitId(Long businessUnitId);

    @Query("SELECT at FROM AccountType at JOIN FETCH at.businessUnit WHERE at.businessUnit.id = :businessUnitId")
    List<AccountType> findByBusinessUnitIdWithBusinessUnit(@Param("businessUnitId") Long businessUnitId);
}
