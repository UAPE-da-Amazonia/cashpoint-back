package io.zhc1.uape.model;

/**
 * User registration information.
 *
 * @param name user full name
 * @param email user email
 * @param username user name
 * @param password user password
 * @param businessUnit user business unit
 * @param role user role
 * @param provider OAuth provider (e.g., "google", "facebook")
 * @param providerId unique ID from the OAuth provider
 */
public record UserRegistry(
        String name,
        String email,
        String username,
        String password,
        BusinessUnit businessUnit,
        UserRole role,
        String provider,
        String providerId) {
    public UserRegistry {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be null or blank.");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email must not be null or blank.");
        }
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("username must not be null or blank.");
        }
        if (businessUnit == null) {
            throw new IllegalArgumentException("businessUnit must not be null.");
        }
        if (role == null) {
            throw new IllegalArgumentException("role must not be null.");
        }
        // Password can be null for OAuth users
        // Provider and providerId can be null for regular users
    }
}
