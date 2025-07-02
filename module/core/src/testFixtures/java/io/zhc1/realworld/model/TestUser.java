package io.zhc1.realworld.model;

import java.util.UUID;

public final class TestUser extends User {
    private final UUID id;

    private static final BusinessUnit DEFAULT_BUSINESS_UNIT = new BusinessUnit("Test Unit");
    private static final UserRole DEFAULT_ROLE = UserRole.USER;

    public TestUser() {
        this(UUID.randomUUID());
    }

    public TestUser(UUID id) {
        this(id, "Test Name", "test@email.com", "testuser", "testpass", DEFAULT_BUSINESS_UNIT, DEFAULT_ROLE);
    }

    public TestUser(
            UUID id,
            String name,
            String email,
            String username,
            String password,
            BusinessUnit businessUnit,
            UserRole role) {
        super(name, email, username, password, businessUnit, role);
        this.id = id;
    }

    @Override
    public UUID getId() {
        return this.id;
    }
}
