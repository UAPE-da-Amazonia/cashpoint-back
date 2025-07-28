package io.zhc1.uape.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import io.zhc1.uape.model.BusinessUnit;
import io.zhc1.uape.model.PasswordEncoder;
import io.zhc1.uape.model.User;
import io.zhc1.uape.model.UserRegistry;
import io.zhc1.uape.model.UserRepository;
import io.zhc1.uape.model.UserRole;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Get user by id.
     *
     * @return Returns user
     */
    public User getUser(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("user not found."));
    }

    /**
     * Get user by username.
     *
     * @return Returns user
     */
    public User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new NoSuchElementException("user not found."));
    }

    /**
     * Register a new user in the system.
     *
     * @param registry User registration information
     * @return Returns the registered user
     */
    @SuppressWarnings("UnusedReturnValue")
    public User signup(UserRegistry registry) {
        if (userRepository.existsBy(registry.email(), registry.username())) {
            throw new IllegalArgumentException("email or username is already exists.");
        }

        var requester = new User(registry);
        if (registry.password() != null && !registry.password().isBlank()) {
            requester.encryptPassword(passwordEncoder, registry.password());
        }

        return userRepository.save(requester);
    }

    /**
     * Login to the system.
     *
     * @param email users email
     * @param password users password
     * @return Returns the logged-in user
     */
    public User login(String email, String password) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email is required.");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("password is required.");
        }

        return userRepository
                .findByEmail(email)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .orElseThrow(() -> new IllegalArgumentException("invalid email or password."));
    }

    /**
     * Process OAuth login or registration.
     *
     * @param email user's email from OAuth provider
     * @param name user's name from OAuth provider
     * @param provider OAuth provider name (e.g., "google")
     * @param providerId unique ID from OAuth provider
     * @param imageUrl user's profile image from OAuth provider
     * @param businessUnit user's business unit
     * @return Returns the logged-in or newly registered user
     */
    public User processOAuthLogin(
            String email, String name, String provider, String providerId, String imageUrl, BusinessUnit businessUnit) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email is required.");
        }
        if (provider == null || provider.isBlank()) {
            throw new IllegalArgumentException("provider is required.");
        }
        if (providerId == null || providerId.isBlank()) {
            throw new IllegalArgumentException("providerId is required.");
        }
        if (businessUnit == null) {
            throw new IllegalArgumentException("businessUnit is required.");
        }

        // First, try to find user by provider and providerId
        var existingUser = userRepository.findByProviderAndProviderId(provider, providerId);
        if (existingUser.isPresent()) {
            var user = existingUser.get();
            // Update user information if needed
            if (imageUrl != null && !imageUrl.isBlank()) {
                user.setImageUrl(imageUrl);
            }
            if (name != null && !name.isBlank() && !name.equals(user.getName())) {
                user.setName(name);
            }
            return userRepository.save(user);
        }

        // If not found by provider, try to find by email within the same business unit
        var userByEmail = userRepository.findByEmail(email);
        if (userByEmail.isPresent()) {
            var user = userByEmail.get();

            // Check if user is in the same business unit
            if (!user.getBusinessUnit().getId().equals(businessUnit.getId())) {
                throw new IllegalArgumentException("User already exists in a different business unit.");
            }

            // Update existing user with OAuth information
            user.setProvider(provider);
            user.setProviderId(providerId);
            if (imageUrl != null && !imageUrl.isBlank()) {
                user.setImageUrl(imageUrl);
            }
            if (name != null && !name.isBlank() && !name.equals(user.getName())) {
                user.setName(name);
            }
            return userRepository.save(user);
        }

        // Create new user with OAuth information
        var username = generateUsernameFromEmail(email);
        var registry = new UserRegistry(
                name,
                email,
                username,
                null, // No password for OAuth users
                businessUnit,
                UserRole.USER,
                provider,
                providerId);

        var newUser = new User(registry);
        if (imageUrl != null && !imageUrl.isBlank()) {
            newUser.setImageUrl(imageUrl);
        }

        return userRepository.save(newUser);
    }

    /**
     * Generate a unique username from email.
     *
     * @param email user's email
     * @return Returns a unique username
     */
    private String generateUsernameFromEmail(String email) {
        String baseUsername = email.split("@")[0];
        String username = baseUsername;
        int counter = 1;

        while (userRepository.existsByUsername(username)) {
            username = baseUsername + counter;
            counter++;
        }

        return username;
    }

    /**
     * Update user information.
     *
     * @param userId The user who requested the update
     * @param name user's full name
     * @param email users email
     * @param username users username
     * @param password users password
     * @param businessUnit user's business unit
     * @param role user's role
     * @param bio users bio
     * @param imageUrl users imageUrl
     * @return Returns the updated user
     */
    public User updateUserDetails(
            UUID userId,
            String name,
            String email,
            String username,
            String password,
            BusinessUnit businessUnit,
            UserRole role,
            String bio,
            String imageUrl) {
        if (userId == null) {
            throw new IllegalArgumentException("user id is required.");
        }

        return userRepository.updateUserDetails(
                userId, passwordEncoder, name, email, username, password, businessUnit, role, bio, imageUrl);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByBusinessUnit(BusinessUnit businessUnit) {
        return userRepository.findByBusinessUnit(businessUnit);
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
