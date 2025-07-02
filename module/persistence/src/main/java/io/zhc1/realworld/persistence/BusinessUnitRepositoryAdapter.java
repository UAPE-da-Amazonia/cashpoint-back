package io.zhc1.realworld.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

import io.zhc1.realworld.config.CacheName;
import io.zhc1.realworld.model.BusinessUnit;
import io.zhc1.realworld.model.BusinessUnitRepository;

@Repository
@RequiredArgsConstructor
class BusinessUnitRepositoryAdapter implements BusinessUnitRepository {
    private final BusinessUnitJpaRepository businessUnitJpaRepository;

    @Override
    @Cacheable(value = CacheName.ALL_BUSINESS_UNITS)
    public List<BusinessUnit> findAll() {
        return businessUnitJpaRepository.findAll();
    }

    @Override
    public BusinessUnit save(BusinessUnit businessUnit) {
        return businessUnitJpaRepository.save(businessUnit);
    }

    @Override
    public Optional<BusinessUnit> findById(Long id) {
        return businessUnitJpaRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        businessUnitJpaRepository.deleteById(id);
    }
}
