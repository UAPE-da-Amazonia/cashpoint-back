package io.zhc1.uape.model;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository {
    Profile save(Profile profile);

    Optional<Profile> findById(Integer id);

    List<Profile> findByBusinessUnit(BusinessUnit businessUnit);

    List<Profile> findByBusinessUnitAndProfileType(BusinessUnit businessUnit, ProfileType profileType);

    List<Profile> findByBusinessUnitAndIsActive(BusinessUnit businessUnit, boolean isActive);

    List<Profile> findByNameContainingIgnoreCaseAndBusinessUnit(String name, BusinessUnit businessUnit);

    boolean existsById(Integer id);

    void delete(Profile profile);
}
