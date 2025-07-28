package io.zhc1.uape.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleOAuth2Service {

    private final RestTemplate restTemplate;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    private static final String GOOGLE_USER_INFO_URL = "https://www.googleapis.com/oauth2/v2/userinfo";

    /**
     * Extract user information from Google OAuth2 token.
     *
     * @param accessToken Google OAuth2 access token
     * @return GoogleUserInfo containing user information
     */
    public GoogleUserInfo extractUserInfo(String accessToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> response =
                    restTemplate.exchange(GOOGLE_USER_INFO_URL, HttpMethod.GET, entity, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> userInfo = response.getBody();

                return GoogleUserInfo.builder()
                        .id((String) userInfo.get("id"))
                        .email((String) userInfo.get("email"))
                        .name((String) userInfo.get("name"))
                        .givenName((String) userInfo.get("given_name"))
                        .familyName((String) userInfo.get("family_name"))
                        .picture((String) userInfo.get("picture"))
                        .verifiedEmail((Boolean) userInfo.get("verified_email"))
                        .build();
            }

            throw new RuntimeException("Failed to extract user info from Google token");

        } catch (Exception e) {
            log.error("Error extracting user info from Google token", e);
            throw new RuntimeException("Invalid Google OAuth2 token", e);
        }
    }

    /** Google user information extracted from OAuth2 token. */
    public static class GoogleUserInfo {
        private final String id;
        private final String email;
        private final String name;
        private final String givenName;
        private final String familyName;
        private final String picture;
        private final Boolean verifiedEmail;

        private GoogleUserInfo(Builder builder) {
            this.id = builder.id;
            this.email = builder.email;
            this.name = builder.name;
            this.givenName = builder.givenName;
            this.familyName = builder.familyName;
            this.picture = builder.picture;
            this.verifiedEmail = builder.verifiedEmail;
        }

        public static Builder builder() {
            return new Builder();
        }

        public String getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public String getName() {
            return name;
        }

        public String getGivenName() {
            return givenName;
        }

        public String getFamilyName() {
            return familyName;
        }

        public String getPicture() {
            return picture;
        }

        public Boolean getVerifiedEmail() {
            return verifiedEmail;
        }

        public static class Builder {
            private String id;
            private String email;
            private String name;
            private String givenName;
            private String familyName;
            private String picture;
            private Boolean verifiedEmail;

            public Builder id(String id) {
                this.id = id;
                return this;
            }

            public Builder email(String email) {
                this.email = email;
                return this;
            }

            public Builder name(String name) {
                this.name = name;
                return this;
            }

            public Builder givenName(String givenName) {
                this.givenName = givenName;
                return this;
            }

            public Builder familyName(String familyName) {
                this.familyName = familyName;
                return this;
            }

            public Builder picture(String picture) {
                this.picture = picture;
                return this;
            }

            public Builder verifiedEmail(Boolean verifiedEmail) {
                this.verifiedEmail = verifiedEmail;
                return this;
            }

            public GoogleUserInfo build() {
                return new GoogleUserInfo(this);
            }
        }
    }
}
