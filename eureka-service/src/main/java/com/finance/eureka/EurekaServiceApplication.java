package com.finance.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka Service - Service Registry
 *
 * Ye service sabhi microservices ko register aur track karti hai.
 * Jaise phone directory mein sabki details hoti hai, waise hi
 * yaha sabhi services ki location aur status store hoti hai.
 *
 * @EnableEurekaServer - Ye annotation is application ko Eureka Server banata hai
 */
@SpringBootApplication
@EnableEurekaServer  // Ye line bahut important hai - isse Eureka Server enable hota hai
public class EurekaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServiceApplication.class, args);
        System.out.println("🚀 Eureka Service is running on http://localhost:8761");
        System.out.println("📊 Eureka Dashboard: http://localhost:8761");
    }
}