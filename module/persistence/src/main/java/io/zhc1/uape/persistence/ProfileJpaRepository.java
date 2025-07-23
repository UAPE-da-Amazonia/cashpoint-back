package io.zhc1.uape.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.zhc1.uape.model.BusinessUnit;
import io.zhc1.uape.model.Profile;
import io.zhc1.uape.model.ProfileType;

@Repository
public interface ProfileJpaRepository extends JpaRepository<Profile, Integer> {

    @EntityGraph(attributePaths = "businessUnit")
    List<Profile> findByBusinessUnit(BusinessUnit businessUnit);

    @EntityGraph(attributePaths = "businessUnit")
    List<Profile> findByBusinessUnitAndProfileType(BusinessUnit businessUnit, ProfileType profileType);

    @EntityGraph(attributePaths = "businessUnit")
    List<Profile> findByBusinessUnitAndIsActive(BusinessUnit businessUnit, boolean isActive);

    @Query(
            "SELECT p FROM Profile p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')) AND p.businessUnit = :businessUnit")
    List<Profile> findByNameContainingIgnoreCaseAndBusinessUnit(
            @Param("name") String name, @Param("businessUnit") BusinessUnit businessUnit);
}
