package io.zhc1.realworld.model;

import java.util.List;
import java.util.Optional;

public interface BusinessUnitRepository {
    List<BusinessUnit> findAll();

    BusinessUnit save(BusinessUnit businessUnit);

    Optional<BusinessUnit> findById(Long id);

    void deleteById(Long id);
}
