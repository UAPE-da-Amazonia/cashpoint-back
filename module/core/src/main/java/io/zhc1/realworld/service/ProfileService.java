package io.zhc1.realworld.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import io.zhc1.realworld.model.BusinessUnit;
import io.zhc1.realworld.model.Profile;
import io.zhc1.realworld.model.ProfileRepository;
import io.zhc1.realworld.model.ProfileType;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final BusinessUnitService businessUnitService;

    public Profile createProfile(
            String name,
            String email,
            String phone,
            String document,
            String address,
            Long businessUnitId,
            ProfileType profileType) {

        BusinessUnit businessUnit = businessUnitService
                .findById(businessUnitId)
                .orElseThrow(() -> new IllegalArgumentException("Business unit not found"));

        Profile profile = new Profile(name, email, phone, document, address, businessUnit, profileType);
        return profileRepository.save(profile);
    }

    public Optional<Profile> findById(Integer id) {
        return profileRepository.findById(id);
    }

    public List<Profile> findByBusinessUnit(Long businessUnitId) {
        BusinessUnit businessUnit = businessUnitService
                .findById(businessUnitId)
                .orElseThrow(() -> new IllegalArgumentException("Business unit not found"));

        return profileRepository.findByBusinessUnit(businessUnit);
    }

    public List<Profile> findByBusinessUnitAndProfileType(Long businessUnitId, ProfileType profileType) {
        BusinessUnit businessUnit = businessUnitService
                .findById(businessUnitId)
                .orElseThrow(() -> new IllegalArgumentException("Business unit not found"));

        return profileRepository.findByBusinessUnitAndProfileType(businessUnit, profileType);
    }

    public List<Profile> findActiveByBusinessUnit(Long businessUnitId) {
        BusinessUnit businessUnit = businessUnitService
                .findById(businessUnitId)
                .orElseThrow(() -> new IllegalArgumentException("Business unit not found"));

        return profileRepository.findByBusinessUnitAndIsActive(businessUnit, true);
    }

    public List<Profile> searchByName(Long businessUnitId, String name) {
        BusinessUnit businessUnit = businessUnitService
                .findById(businessUnitId)
                .orElseThrow(() -> new IllegalArgumentException("Business unit not found"));

        return profileRepository.findByNameContainingIgnoreCaseAndBusinessUnit(name, businessUnit);
    }

    public Profile updateProfile(
            Integer id,
            String name,
            String email,
            String phone,
            String document,
            String address,
            ProfileType profileType) {

        Profile profile =
                profileRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Profile not found"));

        if (name != null) {
            profile.setName(name);
        }
        if (email != null) {
            profile.setEmail(email);
        }
        if (phone != null) {
            profile.setPhone(phone);
        }
        if (document != null) {
            profile.setDocument(document);
        }
        if (address != null) {
            profile.setAddress(address);
        }
        if (profileType != null) {
            profile.setProfileType(profileType);
        }

        return profileRepository.save(profile);
    }

    public void deactivateProfile(Integer id) {
        Profile profile =
                profileRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Profile not found"));

        profile.setActive(false);
        profileRepository.save(profile);
    }

    public void activateProfile(Integer id) {
        Profile profile =
                profileRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Profile not found"));

        profile.setActive(true);
        profileRepository.save(profile);
    }

    public void deleteProfile(Integer id) {
        Profile profile =
                profileRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Profile not found"));

        profileRepository.delete(profile);
    }
}
