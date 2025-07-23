package io.zhc1.uape.config;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.Transient;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Transient
public final class AuthToken extends AbstractOAuth2TokenAuthenticationToken<Jwt> {
    private final JwtAuthenticationToken delegate;

    public AuthToken(Jwt jwt, JwtAuthenticationToken delegate) {
        super(jwt);
        this.delegate = delegate;
    }

    @Override
    public Object getPrincipal() {
        return this.delegate.getPrincipal();
    }

    @Override
    public Object getCredentials() {
        return this.delegate.getCredentials();
    }

    @Override
    public void eraseCredentials() {
        this.delegate.eraseCredentials();
    }

    @Override
    public void setDetails(Object details) {
        this.delegate.setDetails(details);
    }

    @Override
    public Object getDetails() {
        return this.delegate.getDetails();
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        this.delegate.setAuthenticated(authenticated);
    }

    @Override
    public boolean isAuthenticated() {
        return this.delegate.isAuthenticated();
    }

    @Override
    public String getName() {
        return this.delegate.getName();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.delegate.getAuthorities();
    }

    @Override
    public Map<String, Object> getTokenAttributes() {
        return this.delegate.getTokenAttributes();
    }

    /**
     * Returns a JWT. It usually refers to a token string expressing with 'eyXXX.eyXXX.eyXXX' format.
     *
     * @return the token value as a String
     */
    public String tokenValue() {
        return delegate.getToken().getTokenValue();
    }

    /**
     * Extract Subject from JWT. Here, Subject is the user ID in UUID format.
     *
     * @return the user ID as a UUID
     */
    public UUID userId() {
        return UUID.fromString(delegate.getName());
    }

    /**
     * Extract BusinessUnit ID from JWT.
     *
     * @return the business unit ID as a Long
     */
    public Long businessUnitId() {
        return delegate.getToken().getClaim("businessUnitId");
    }

    /**
     * Extract Role from JWT.
     *
     * @return the user role as a String
     */
    public String role() {
        return delegate.getToken().getClaim("role");
    }

    /**
     * Extract Username from JWT.
     *
     * @return the username as a String
     */
    public String username() {
        return delegate.getToken().getClaim("username");
    }

    /**
     * Check if user is ADMIN.
     *
     * @return true if user is ADMIN
     */
    public boolean isAdmin() {
        return "ADMIN".equals(role());
    }

    /**
     * Check if user is USER.
     *
     * @return true if user is USER
     */
    public boolean isUser() {
        return "USER".equals(role());
    }
}
