package io.zhc1.uape.api.request;

/**
 * Request for Google OAuth2 login.
 *
 * @param accessToken Google OAuth2 access token
 * @param businessUnitId ID da business unit do usuário
 */
public record GoogleLoginRequest(String accessToken, Long businessUnitId) {
    public GoogleLoginRequest {
        if (accessToken == null || accessToken.isBlank()) {
            throw new IllegalArgumentException("accessToken must not be null or blank.");
        }
        if (businessUnitId == null) {
            throw new IllegalArgumentException("businessUnitId must not be null.");
        }
    }
}
