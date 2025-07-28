package io.zhc1.uape.model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    List<User> findAll();

    List<User> findByBusinessUnit(BusinessUnit businessUnit);

    User save(User user);

    Optional<User> findById(UUID id);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByProviderAndProviderId(String provider, String providerId);

    Optional<User> findByEmailAndProvider(String email, String provider);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsBy(String email, String username);

    boolean existsByProviderAndProviderId(String provider, String providerId);

    User updateUserDetails(
            UUID userId,
            PasswordEncoder passwordEncoder,
            String name,
            String email,
            String username,
            String password,
            BusinessUnit businessUnit,
            UserRole role,
            String bio,
            String imageUrl);

    void deleteById(UUID id);
}
