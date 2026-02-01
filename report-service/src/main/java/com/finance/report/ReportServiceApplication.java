package com.finance.report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Report Service
 *
 * @EnableFeignClients - Inter-service communication ke liye
 * Ye Transaction Service se data fetch karega
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients  // Important: Feign clients enable karne ke liye
public class ReportServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReportServiceApplication.class, args);
        System.out.println("📊 Report Service is running on http://localhost:8084");
    }
}