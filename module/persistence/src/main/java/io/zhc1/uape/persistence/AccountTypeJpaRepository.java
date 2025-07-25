package io.zhc1.uape.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.zhc1.uape.model.AccountType;

public interface AccountTypeJpaRepository extends JpaRepository<AccountType, Integer> {

    @EntityGraph(attributePaths = {"businessUnit"})
    List<AccountType> findByBusinessUnit_Id(Long businessUnitId);

    @Query("SELECT at FROM AccountType at JOIN FETCH at.businessUnit WHERE at.businessUnit.id = :businessUnitId")
    List<AccountType> findByBusinessUnitIdWithBusinessUnit(@Param("businessUnitId") Long businessUnitId);
}
