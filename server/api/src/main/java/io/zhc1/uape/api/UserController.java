package io.zhc1.uape.api;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import io.zhc1.uape.api.request.LoginUserRequest;
import io.zhc1.uape.api.request.SignupRequest;
import io.zhc1.uape.api.request.UpdateUserRequest;
import io.zhc1.uape.api.response.UserResponse;
import io.zhc1.uape.api.response.UsersResponse;
import io.zhc1.uape.config.AuthToken;
import io.zhc1.uape.config.AuthTokenProvider;
import io.zhc1.uape.model.BusinessUnit;
import io.zhc1.uape.model.User;
import io.zhc1.uape.model.UserRegistry;
import io.zhc1.uape.model.UserRole;
import io.zhc1.uape.service.BusinessUnitService;
import io.zhc1.uape.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
class UserController {
    private final UserService userService;
    private final BusinessUnitService businessUnitService;
    private final AuthTokenProvider bearerTokenProvider;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, UserResponse> signup(@RequestBody SignupRequest request) {
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

        return Map.of("user", UserResponse.from(user, token));
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, UserResponse> login(@RequestBody LoginUserRequest request) {
        var email = request.user().email();
        var password = request.user().password();

        var user = userService.login(email, password);
        var authToken = bearerTokenProvider.createAuthToken(user);

        return Map.of("user", UserResponse.from(user, authToken));
    }

    @GetMapping("/me")
    public UsersResponse getUser(AuthToken actorsToken) {
        var actor = userService.getUser(actorsToken.userId());

        return UsersResponse.from(actor, actorsToken.tokenValue());
    }

    @PutMapping("/me")
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

    @GetMapping("/test")
    public String test() {
        return "UserController is working!";
    }

    @GetMapping
    @ResponseBody
    public UsersResponse getUsers(AuthToken authToken) {
        List<User> users;
        if (authToken.isAdmin()) {
            users = userService.getAllUsers();
        } else {
            BusinessUnit businessUnit = businessUnitService
                    .findById(authToken.businessUnitId())
                    .orElseThrow(() -> new RuntimeException("BusinessUnit não encontrada"));
            users = userService.getUsersByBusinessUnit(businessUnit);
        }
        return UsersResponse.from(users);
    }

    @PutMapping("/{id}")
    public UsersResponse updateAnyUser(
            AuthToken authToken, @PathVariable UUID id, @RequestBody UpdateUserRequest request) {
        if (!authToken.isAdmin()) {
            throw new SecurityException("Apenas ADMIN pode editar outros usuários.");
        }
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
        User user = userService.updateUserDetails(
                id,
                request.user().name(),
                request.user().email(),
                request.user().username(),
                request.user().password(),
                businessUnit,
                role,
                request.user().bio(),
                request.user().image());
        return UsersResponse.from(user, authToken.tokenValue());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(AuthToken authToken, @PathVariable UUID id) {
        if (!authToken.isAdmin()) {
            throw new SecurityException("Apenas ADMIN pode deletar usuários.");
        }
        userService.deleteUser(id);
    }
}
