package io.zhc1.uape.api.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.zhc1.uape.model.Profile;
import io.zhc1.uape.model.ProfileType;

public record ProfileResponse(
        Integer id,
        String name,
        String email,
        String phone,
        String document,
        String address,
        String businessUnitName,
        @JsonProperty("profile_type") ProfileType profileType,
        @JsonProperty("is_active") boolean isActive,
        @JsonProperty("created_at") LocalDateTime createdAt,
        @JsonProperty("updated_at") LocalDateTime updatedAt) {
    public static ProfileResponse from(Profile profile) {
        return new ProfileResponse(
                profile.getId(),
                profile.getName(),
                profile.getEmail(),
                profile.getPhone(),
                profile.getDocument(),
                profile.getAddress(),
                profile.getBusinessUnit() != null ? profile.getBusinessUnit().getName() : null,
                profile.getProfileType(),
                profile.isActive(),
                profile.getCreatedAt(),
                profile.getUpdatedAt());
    }
}
