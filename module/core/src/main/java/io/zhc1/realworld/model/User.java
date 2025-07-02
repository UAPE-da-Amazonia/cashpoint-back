package io.zhc1.realworld.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(length = 30, nullable = false, unique = true)
    private String email;

    @Column(length = 30, nullable = false, unique = true)
    private String username;

    @Column(length = 200, nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_unit_id", nullable = false)
    private BusinessUnit businessUnit;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role = UserRole.USER;

    @Column(length = 500)
    private String bio;

    @Column(length = 200)
    private String imageUrl;

    @Column(nullable = false, updatable = false)
    private final LocalDateTime createdAt = LocalDateTime.now();

    public User(UserRegistry registry) {
        this(
                registry.name(),
                registry.email(),
                registry.username(),
                registry.password(),
                registry.businessUnit(),
                registry.role());
    }

    public User(String name, String email, String username, String password, BusinessUnit businessUnit, UserRole role) {
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

        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.businessUnit = businessUnit;
        this.role = role;
    }

    public boolean equalsEmail(String email) {
        return this.email.equals(email);
    }

    public boolean equalsUsername(String username) {
        return this.username.equals(username);
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            log.warn("not set because the name is empty. name={}", name);
            return;
        }
        this.name = name;
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank() || this.email.equals(email)) {
            log.warn("not set because the email is empty or equals. email={}", email);
            return;
        }

        // Note: You can add some more validations here if you want. (ex. regex)
        this.email = email;
    }

    public void setUsername(String username) {
        if (username == null || username.isBlank() || this.username.equals(username)) {
            log.warn("not set because the username is empty or equals. username={}", username);
            return;
        }

        // Note: You can add some more validations here if you want. (ex. regex)
        this.username = username;
    }

    public void setBusinessUnit(BusinessUnit businessUnit) {
        if (businessUnit == null) {
            log.warn("not set because the businessUnit is null.");
            return;
        }
        this.businessUnit = businessUnit;
    }

    public void setRole(UserRole role) {
        if (role == null) {
            log.warn("not set because the role is null.");
            return;
        }
        this.role = role;
    }

    public void encryptPassword(PasswordEncoder passwordEncoder, String plainPassword) {
        if (passwordEncoder == null) {
            throw new IllegalArgumentException("passwordEncoder is required.");
        }

        if (plainPassword == null || plainPassword.isBlank()) {
            log.warn("not set because the rawPassword is empty.");
            return;
        }

        if (passwordEncoder.matches(plainPassword, this.password)) {
            log.warn("not set because the rawPassword is same as current password.");
            return;
        }

        // Note: You can add some more validations here if you want. (ex. regex)
        this.password = passwordEncoder.encode(plainPassword);
    }

    public void setBio(String bio) {
        if (bio != null && bio.isBlank()) {
            log.warn("not set because the bio is empty. bio={}", bio);
            return;
        }

        this.bio = bio;
    }

    public void setImageUrl(String imageUrl) {
        if (imageUrl != null && imageUrl.isBlank()) {
            log.warn("not set because the imageUrl is empty. imageUrl={}", imageUrl);
            return;
        }

        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof User other && Objects.equals(this.getId(), other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
