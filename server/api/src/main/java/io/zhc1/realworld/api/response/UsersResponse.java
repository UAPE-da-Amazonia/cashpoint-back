package io.zhc1.realworld.api.response;

import java.util.List;

import io.zhc1.realworld.model.User;

public record UsersResponse(List<UserResponse> users) {
    public static UsersResponse from(User user, String token) {
        UserResponse userResponse = new UserResponse(
                user.getName(),
                user.getEmail(),
                token,
                user.getUsername(),
                new UserResponse.BusinessUnitData(user.getBusinessUnit()),
                user.getRole().name(),
                user.getBio(),
                user.getImageUrl());
        return new UsersResponse(List.of(userResponse));
    }

    public static UsersResponse from(List<User> users) {
        List<UserResponse> userResponses = users.stream()
                .map(user -> new UserResponse(
                        user.getName(),
                        user.getEmail(),
                        null, // token não é necessário para listagem
                        user.getUsername(),
                        new UserResponse.BusinessUnitData(user.getBusinessUnit()),
                        user.getRole().name(),
                        user.getBio(),
                        user.getImageUrl()))
                .toList();
        return new UsersResponse(userResponses);
    }
}
