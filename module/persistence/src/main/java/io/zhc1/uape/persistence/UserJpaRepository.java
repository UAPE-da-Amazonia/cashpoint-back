package io.zhc1.uape.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.zhc1.uape.model.BusinessUnit;
import io.zhc1.uape.model.User;

interface UserJpaRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByProviderAndProviderId(String provider, String providerId);

    Optional<User> findByEmailAndProvider(String email, String provider);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmailOrUsername(String email, String username);

    boolean existsByProviderAndProviderId(String provider, String providerId);

    List<User> findByBusinessUnit(BusinessUnit businessUnit);
}
