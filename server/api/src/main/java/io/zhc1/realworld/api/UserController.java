package io.zhc1.realworld.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import io.zhc1.realworld.api.request.LoginUserRequest;
import io.zhc1.realworld.api.request.SignupRequest;
import io.zhc1.realworld.api.request.UpdateUserRequest;
import io.zhc1.realworld.api.response.UsersResponse;
import io.zhc1.realworld.config.AuthToken;
import io.zhc1.realworld.config.AuthTokenProvider;
import io.zhc1.realworld.model.BusinessUnit;
import io.zhc1.realworld.model.User;
import io.zhc1.realworld.model.UserRegistry;
import io.zhc1.realworld.model.UserRole;
import io.zhc1.realworld.service.BusinessUnitService;
import io.zhc1.realworld.service.UserService;

@RestController
@RequiredArgsConstructor
class UserController {
    private static final String LOGIN_URL = "/api/users/login";

    private final UserService userService;
    private final BusinessUnitService businessUnitService;
    private final AuthTokenProvider bearerTokenProvider;

    @PostMapping("/api/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UsersResponse signup(@RequestBody SignupRequest request) {
        BusinessUnit businessUnit = businessUnitService
                .findById(request.user().businessUnitId())
                .orElseThrow(() -> new RuntimeException("BusinessUnit não encontrada"));

        UserRole role = UserRole.valueOf(request.user().role().toUpperCase());

        var userRegistry = new UserRegistry(
                request.user().name(),
                request.user().email(),
                request.user().username(),
                request.user().password(),
                businessUnit,
                role);

        User user = userService.signup(userRegistry);
        String token = bearerTokenProvider.createAuthToken(user);

        return UsersResponse.from(user, token);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(LOGIN_URL)
    public UsersResponse login(@RequestBody LoginUserRequest request) {
        var email = request.user().email();
        var password = request.user().password();

        var user = userService.login(email, password);
        var authToken = bearerTokenProvider.createAuthToken(user);

        return UsersResponse.from(user, authToken);
    }

    @GetMapping("/api/user")
    public UsersResponse getUser(AuthToken actorsToken) {
        var actor = userService.getUser(actorsToken.userId());

        return UsersResponse.from(actor, actorsToken.tokenValue());
    }

    @PutMapping("/api/user")
    public UsersResponse updateUser(AuthToken actorsToken, @RequestBody UpdateUserRequest request) {
        BusinessUnit businessUnit = null;
        if (request.user().businessUnitId() != null) {
            businessUnit = businessUnitService
                    .findById(request.user().businessUnitId())
                    .orElseThrow(() -> new RuntimeException("BusinessUnit não encontrada"));
        }

        UserRole role = null;
        if (request.user().role() != null) {
            role = UserRole.valueOf(request.user().role().toUpperCase());
        }

        User actor = userService.updateUserDetails(
                actorsToken.userId(),
                request.user().name(),
                request.user().email(),
                request.user().username(),
                request.user().password(),
                businessUnit,
                role,
                request.user().bio(),
                request.user().image());

        return UsersResponse.from(actor, actorsToken.tokenValue());
    }
}
