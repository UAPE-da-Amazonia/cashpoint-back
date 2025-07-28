package io.zhc1.uape.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import io.zhc1.uape.model.BusinessUnit;
import io.zhc1.uape.model.PasswordEncoder;
import io.zhc1.uape.model.User;
import io.zhc1.uape.model.UserRepository;
import io.zhc1.uape.model.UserRole;

@Repository
@RequiredArgsConstructor
class UserRepositoryAdapter implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userJpaRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByProviderAndProviderId(String provider, String providerId) {
        return userJpaRepository.findByProviderAndProviderId(provider, providerId);
    }

    @Override
    public Optional<User> findByEmailAndProvider(String email, String provider) {
        return userJpaRepository.findByEmailAndProvider(email, provider);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userJpaRepository.existsByUsername(username);
    }

    @Override
    public boolean existsBy(String email, String username) {
        return userJpaRepository.existsByEmailOrUsername(email, username);
    }

    @Override
    public boolean existsByProviderAndProviderId(String provider, String providerId) {
        return userJpaRepository.existsByProviderAndProviderId(provider, providerId);
    }

    @Override
    public List<User> findAll() {
        return userJpaRepository.findAll();
    }

    @Override
    public List<User> findByBusinessUnit(BusinessUnit businessUnit) {
        return userJpaRepository.findByBusinessUnit(businessUnit);
    }

    @Override
    @Transactional
    public User updateUserDetails(
            UUID userId,
            PasswordEncoder passwordEncoder,
            String name,
            String email,
            String username,
            String password,
            BusinessUnit businessUnit,
            UserRole role,
            String bio,
            String imageUrl) {
        return this.findById(userId)
                .map(user -> {
                    if (email != null && !user.equalsEmail(email) && this.existsByEmail(email)) {
                        throw new IllegalArgumentException("email is already exists.");
                    }

                    if (username != null && !user.equalsUsername(username) && this.existsByUsername(username)) {
                        throw new IllegalArgumentException("username is already exists.");
                    }

                    if (name != null) {
                        user.setName(name);
                    }
                    if (email != null) {
                        user.setEmail(email);
                    }
                    if (username != null) {
                        user.setUsername(username);
                    }
                    if (password != null && !password.isBlank()) {
                        user.encryptPassword(passwordEncoder, password);
                    }
                    if (businessUnit != null) {
                        user.setBusinessUnit(businessUnit);
                    }
                    if (role != null) {
                        user.setRole(role);
                    }
                    if (bio != null) {
                        user.setBio(bio);
                    }
                    if (imageUrl != null) {
                        user.setImageUrl(imageUrl);
                    }
                    return userJpaRepository.save(user);
                })
                .orElseThrow(() -> new IllegalArgumentException("user not found."));
    }

    @Override
    public void deleteById(UUID id) {
        userJpaRepository.deleteById(id);
    }
}
