package io.zhc1.uape.api;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import io.zhc1.uape.api.request.GoogleLoginRequest;
import io.zhc1.uape.api.response.UserResponse;
import io.zhc1.uape.config.AuthTokenProvider;
import io.zhc1.uape.config.GoogleOAuth2Service;
import io.zhc1.uape.service.BusinessUnitService;
import io.zhc1.uape.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
class OAuth2Controller {

    private final UserService userService;
    private final BusinessUnitService businessUnitService;
    private final AuthTokenProvider authTokenProvider;
    private final GoogleOAuth2Service googleOAuth2Service;

    @PostMapping("/google/login")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, UserResponse> googleLogin(@RequestBody GoogleLoginRequest request) {
        // Extract user information from Google token
        var googleUserInfo = googleOAuth2Service.extractUserInfo(request.accessToken());

        // Get business unit from request
        var businessUnit = businessUnitService
                .findById(request.businessUnitId())
                .orElseThrow(() -> new RuntimeException("BusinessUnit não encontrada"));

        // Process OAuth login/registration
        var user = userService.processOAuthLogin(
                googleUserInfo.getEmail(),
                googleUserInfo.getName(),
                "google",
                googleUserInfo.getId(),
                googleUserInfo.getPicture(),
                businessUnit);

        // Generate JWT token
        var token = authTokenProvider.createAuthToken(user);

        return Map.of("user", UserResponse.from(user, token));
    }
}
