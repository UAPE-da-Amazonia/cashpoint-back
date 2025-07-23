package io.zhc1.uape.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.server.resource.BearerTokenErrors;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.util.StringUtils;

/** Change the prefix of the Authorization token from 'Bearer' to 'Token'. */
class AuthTokenResolver implements BearerTokenResolver {
    private static final Pattern AUTHORIZATION_PATTERN =
            Pattern.compile("^Token (?<token>[a-zA-Z0-9-._~+/]+=*)$", Pattern.CASE_INSENSITIVE);

    private static final String ACCESS_TOKEN_PARAM = "access_token";

    @Override
    public String resolve(HttpServletRequest request) {
        String authorizationHeaderToken = resolveFromAuthorizationHeader(request);
        String parameterToken =
                isParameterTokenSupportedForRequest(request) ? resolveFromRequestParameters(request) : null;

        if (authorizationHeaderToken != null) {
            if (parameterToken != null) {
                throw new OAuth2AuthenticationException(
                        BearerTokenErrors.invalidRequest("Found multiple bearer tokens in the request"));
            }
            return authorizationHeaderToken;
        }

        return parameterToken;
    }

    private String resolveFromAuthorizationHeader(HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorization == null) {
            return null;
        }

        // Aceita tanto 'Token' quanto 'Bearer' como prefixo
        if (StringUtils.startsWithIgnoreCase(authorization, "token")) {
            Matcher matcher = AUTHORIZATION_PATTERN.matcher(authorization);
            if (!matcher.matches()) {
                throw new OAuth2AuthenticationException(BearerTokenErrors.invalidToken("Bearer token is malformed"));
            }
            return matcher.group("token");
        } else if (StringUtils.startsWithIgnoreCase(authorization, "bearer")) {
            // Padrão do Spring Security
            String[] parts = authorization.split(" ");
            if (parts.length == 2) {
                return parts[1];
            } else {
                throw new OAuth2AuthenticationException(BearerTokenErrors.invalidToken("Bearer token is malformed"));
            }
        }
        return null;
    }

    private boolean isParameterTokenSupportedForRequest(HttpServletRequest request) {
        return (("POST".equals(request.getMethod())
                        && MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(request.getContentType()))
                || "GET".equals(request.getMethod()));
    }

    private String resolveFromRequestParameters(HttpServletRequest request) {
        String[] values = request.getParameterValues(ACCESS_TOKEN_PARAM);

        if (values == null || values.length == 0) {
            return null;
        }

        if (values.length == 1) {
            return values[0];
        }

        throw new OAuth2AuthenticationException(
                BearerTokenErrors.invalidRequest("Found multiple bearer tokens in the request"));
    }
}
