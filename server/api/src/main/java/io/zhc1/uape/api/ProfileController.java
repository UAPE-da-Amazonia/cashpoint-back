package io.zhc1.uape.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import io.zhc1.uape.api.request.CreateProfileRequest;
import io.zhc1.uape.api.request.UpdateProfileRequest;
import io.zhc1.uape.api.response.ProfileResponse;
import io.zhc1.uape.api.response.ProfilesResponse;
import io.zhc1.uape.config.AuthToken;
import io.zhc1.uape.model.Profile;
import io.zhc1.uape.model.ProfileType;
import io.zhc1.uape.service.ProfileService;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping
    public ResponseEntity<ProfileResponse> createProfile(
            AuthToken authToken, @RequestBody CreateProfileRequest request) {
        ProfileType profileType = ProfileType.valueOf(request.profileType().toUpperCase());

        Profile profile = profileService.createProfile(
                request.name(),
                request.email(),
                request.phone(),
                request.document(),
                request.address(),
                1L,
                profileType);

        return ResponseEntity.status(HttpStatus.CREATED).body(ProfileResponse.from(profile));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponse> getProfile(AuthToken authToken, @PathVariable Integer id) {
        Profile profile =
                profileService.findById(id).orElseThrow(() -> new IllegalArgumentException("Profile not found"));

        return ResponseEntity.ok(ProfileResponse.from(profile));
    }

    @GetMapping
    public ResponseEntity<ProfilesResponse> getProfiles(
            AuthToken authToken,
            @RequestParam(defaultValue = "1") Long businessUnitId,
            @RequestParam(required = false) String profileType,
            @RequestParam(required = false) String search) {

        List<Profile> profiles;

        if (search != null && !search.isBlank()) {
            profiles = profileService.searchByName(businessUnitId, search);
        } else if (profileType != null && !profileType.isBlank()) {
            ProfileType type = ProfileType.valueOf(profileType.toUpperCase());
            profiles = profileService.findByBusinessUnitAndProfileType(businessUnitId, type);
        } else {
            profiles = profileService.findActiveByBusinessUnit(businessUnitId);
        }

        return ResponseEntity.ok(ProfilesResponse.from(profiles));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfileResponse> updateProfile(
            AuthToken authToken, @PathVariable Integer id, @RequestBody UpdateProfileRequest request) {

        ProfileType profileType = null;
        if (request.profileType() != null) {
            profileType = ProfileType.valueOf(request.profileType().toUpperCase());
        }

        Profile profile = profileService.updateProfile(
                id,
                request.name(),
                request.email(),
                request.phone(),
                request.document(),
                request.address(),
                profileType);

        return ResponseEntity.ok(ProfileResponse.from(profile));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(AuthToken authToken, @PathVariable Integer id) {
        profileService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<ProfileResponse> activateProfile(AuthToken authToken, @PathVariable Integer id) {
        profileService.activateProfile(id);
        Profile profile =
                profileService.findById(id).orElseThrow(() -> new IllegalArgumentException("Profile not found"));

        return ResponseEntity.ok(ProfileResponse.from(profile));
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<ProfileResponse> deactivateProfile(AuthToken authToken, @PathVariable Integer id) {
        profileService.deactivateProfile(id);
        Profile profile =
                profileService.findById(id).orElseThrow(() -> new IllegalArgumentException("Profile not found"));

        return ResponseEntity.ok(ProfileResponse.from(profile));
    }
}
