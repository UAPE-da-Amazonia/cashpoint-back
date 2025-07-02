package io.zhc1.realworld.api.request;

public record UpdateUserRequest(Params user) {
    public record Params(
            String name,
            String email,
            String username,
            String password,
            Long businessUnitId,
            String role,
            String bio,
            String image) {}
}
