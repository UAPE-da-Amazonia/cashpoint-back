package io.zhc1.realworld.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.zhc1.realworld.model.BusinessUnit;
import io.zhc1.realworld.model.Profile;
import io.zhc1.realworld.model.ProfileType;

@Repository
public interface ProfileJpaRepository extends JpaRepository<Profile, Integer> {

    List<Profile> findByBusinessUnit(BusinessUnit businessUnit);

    List<Profile> findByBusinessUnitAndProfileType(BusinessUnit businessUnit, ProfileType profileType);

    List<Profile> findByBusinessUnitAndIsActive(BusinessUnit businessUnit, boolean isActive);

    @Query(
            "SELECT p FROM Profile p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')) AND p.businessUnit = :businessUnit")
    List<Profile> findByNameContainingIgnoreCaseAndBusinessUnit(
            @Param("name") String name, @Param("businessUnit") BusinessUnit businessUnit);
}
