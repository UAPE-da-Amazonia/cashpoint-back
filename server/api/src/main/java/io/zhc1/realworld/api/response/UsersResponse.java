package io.zhc1.realworld.api.response;

import io.zhc1.realworld.model.User;

public record UsersResponse(UserResponse user) {
    public static UsersResponse from(User user, String token) {
        return new UsersResponse(new UserResponse(
                user.getName(),
                user.getEmail(),
                token,
                user.getUsername(),
                new UserResponse.BusinessUnitData(user.getBusinessUnit()),
                user.getRole().name(),
                user.getBio(),
                user.getImageUrl()));
    }
}
