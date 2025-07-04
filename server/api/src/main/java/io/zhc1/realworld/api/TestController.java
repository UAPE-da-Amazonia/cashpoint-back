package io.zhc1.realworld.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping
    public String test() {
        return "TestController is working!";
    }

    @GetMapping("/users")
    public String testUsers() {
        return "Test users endpoint is working!";
    }
}
