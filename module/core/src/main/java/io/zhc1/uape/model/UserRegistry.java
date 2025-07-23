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
 */
public record UserRegistry(
        String name, String email, String username, String password, BusinessUnit businessUnit, UserRole role) {
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
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("password must not be null or blank.");
        }
        if (businessUnit == null) {
            throw new IllegalArgumentException("businessUnit must not be null.");
        }
        if (role == null) {
            throw new IllegalArgumentException("role must not be null.");
        }
    }
}
