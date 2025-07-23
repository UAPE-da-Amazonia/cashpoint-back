package io.zhc1.uape;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("Uape Application - Spring Context Initialization")
public class uape {
    @Test
    @DisplayName("When application starts, then Spring context should load successfully")
    void contextLoads() {
        assertTrue(true);
    }
}
