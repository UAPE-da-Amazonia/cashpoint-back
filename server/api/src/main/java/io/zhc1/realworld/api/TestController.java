package io.zhc1.realworld.api;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.zhc1.realworld.config.AuthToken;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping
    public String test(AuthToken authToken) {
        return "TestController is working!";
    }

    @GetMapping("/users")
    public String testUsers(AuthToken authToken) {
        return "Test users endpoint is working!";
    }

    @GetMapping("/cors")
    public ResponseEntity<Map<String, String>> testCors(AuthToken authToken) {
        return ResponseEntity.ok(Map.of("message", "CORS is working!", "status", "success"));
    }

    @PostMapping("/cors")
    public ResponseEntity<Map<String, String>> testCorsPost(
            AuthToken authToken, @RequestBody Map<String, Object> request) {
        return ResponseEntity.ok(Map.of(
                "message", "CORS POST is working!",
                "status", "success",
                "received", request.toString()));
    }
}
