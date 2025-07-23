package io.zhc1.uape.api.request;

public record SignupRequest(Params user) {
    public record Params(
            String name, String email, String username, String password, Long businessUnitId, String role) {}
}
