package com.finance.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * API Gateway Application
 *
 * Ye application ka main entry point hai. Sabhi requests pehle yaha aati hain,
 * phir ye unhe sahi microservice tak route kar deta hai.
 *
 * Analogy: Jaise ek shopping mall mein main gate hota hai, aur waha se
 * security guard aapko sahi shop tak guide karta hai.
 *
 * @EnableDiscoveryClient - Ye annotation isse Eureka se connect karta hai
 */
@SpringBootApplication
@EnableDiscoveryClient  // Eureka client enable karta hai
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
        System.out.println("🚪 API Gateway is running on http://localhost:8080");
        System.out.println("📡 All requests will be routed through this gateway");
    }
}