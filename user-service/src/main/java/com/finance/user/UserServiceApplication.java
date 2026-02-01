package com.finance.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import jakarta.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * User Service Application
 *
 * Ye microservice user management handle karti hai:
 * - User registration
 * - User login
 * - Profile management
 * - Authentication
 */
@SpringBootApplication
@EnableDiscoveryClient  // Eureka client enable
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
        System.out.println("👤 User Service is running on http://localhost:8081");
        System.out.println("📝 Register: POST /api/users/register");
        System.out.println("🔐 Login: POST /api/users/login");
    }

    @PostConstruct
    public void init() {
        // Timezone ko forcefully Asia/Kolkata set kar rahe hain
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
    }
}