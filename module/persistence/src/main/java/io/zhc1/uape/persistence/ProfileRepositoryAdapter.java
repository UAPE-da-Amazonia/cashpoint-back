package io.zhc1.uape.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

import io.zhc1.uape.model.BusinessUnit;
import io.zhc1.uape.model.Profile;
import io.zhc1.uape.model.ProfileRepository;
import io.zhc1.uape.model.ProfileType;

@Repository
@RequiredArgsConstructor
public class ProfileRepositoryAdapter implements ProfileRepository {

    private final ProfileJpaRepository profileJpaRepository;

    @Override
    @CacheEvict(
            value = {"profiles", "profilesByBusinessUnit"},
            allEntries = true)
    public Profile save(Profile profile) {
        return profileJpaRepository.save(profile);
    }

    @Override
    @Cacheable(value = "profiles", key = "#id")
    public Optional<Profile> findById(Integer id) {
        return profileJpaRepository.findById(id);
    }

    @Override
    @Cacheable(value = "profilesByBusinessUnit", key = "#businessUnit.id")
    public List<Profile> findByBusinessUnit(BusinessUnit businessUnit) {
        return profileJpaRepository.findByBusinessUnit(businessUnit);
    }

    @Override
    @Cacheable(value = "profilesByBusinessUnit", key = "#businessUnit.id + '_' + #profileType")
    public List<Profile> findByBusinessUnitAndProfileType(BusinessUnit businessUnit, ProfileType profileType) {
        return profileJpaRepository.findByBusinessUnitAndProfileType(businessUnit, profileType);
    }

    @Override
    @Cacheable(value = "profilesByBusinessUnit", key = "#businessUnit.id + '_active_' + #isActive")
    public List<Profile> findByBusinessUnitAndIsActive(BusinessUnit businessUnit, boolean isActive) {
        return profileJpaRepository.findByBusinessUnitAndIsActive(businessUnit, isActive);
    }

    @Override
    public List<Profile> findByNameContainingIgnoreCaseAndBusinessUnit(String name, BusinessUnit businessUnit) {
        return profileJpaRepository.findByNameContainingIgnoreCaseAndBusinessUnit(name, businessUnit);
    }

    @Override
    public boolean existsById(Integer id) {
        return profileJpaRepository.existsById(id);
    }

    @Override
    @CacheEvict(
            value = {"profiles", "profilesByBusinessUnit"},
            allEntries = true)
    public void delete(Profile profile) {
        profileJpaRepository.delete(profile);
    }
}
