package io.zhc1.uape.api.response;

import io.zhc1.uape.model.BusinessUnit;
import io.zhc1.uape.model.User;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String email,
        String token,
        String username,
        BusinessUnitData businessUnit,
        String role,
        String bio,
        String image) {

    public record BusinessUnitData(Long id, String name, String description) {
        public BusinessUnitData(BusinessUnit businessUnit) {
            this(businessUnit.getId(), businessUnit.getName(), businessUnit.getDescription());
        }
    }

    public static UserResponse from(User user, String token) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                token,
                user.getUsername(),
                new BusinessUnitData(user.getBusinessUnit()),
                user.getRole().name(),
                user.getBio(),
                user.getImageUrl());
    }
}
