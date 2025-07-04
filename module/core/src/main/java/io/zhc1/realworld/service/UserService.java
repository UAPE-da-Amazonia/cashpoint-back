package io.zhc1.realworld.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import io.zhc1.realworld.model.BusinessUnit;
import io.zhc1.realworld.model.PasswordEncoder;
import io.zhc1.realworld.model.User;
import io.zhc1.realworld.model.UserRegistry;
import io.zhc1.realworld.model.UserRepository;
import io.zhc1.realworld.model.UserRole;

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
        requester.encryptPassword(passwordEncoder, registry.password());

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
