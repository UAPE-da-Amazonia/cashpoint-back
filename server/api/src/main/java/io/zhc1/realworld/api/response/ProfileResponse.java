package io.zhc1.realworld.api.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.zhc1.realworld.model.Profile;
import io.zhc1.realworld.model.ProfileType;

public record ProfileResponse(
        Integer id,
        String name,
        String email,
        String phone,
        String document,
        String address,
        @JsonProperty("business_unit_id") Long businessUnitId,
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
                profile.getBusinessUnit().getId(),
                profile.getProfileType(),
                profile.isActive(),
                profile.getCreatedAt(),
                profile.getUpdatedAt());
    }
}
