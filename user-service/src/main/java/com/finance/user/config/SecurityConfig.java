package com.finance.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security Configuration
 *
 * Ye class security settings define karti hai:
 * - Kaunse endpoints public hain
 * - Kaunse endpoints protected hain
 * - Password encryption kaise hoga
 *
 * @Configuration - Ye batata hai ki ye configuration class hai
 * @EnableWebSecurity - Web security enable karta hai
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Security Filter Chain
     *
     * Ye define karta hai ki kaunse routes protected hain aur kaunse public
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF disable (JWT use kar rahe hain to zarurat nahi)
                .csrf(csrf -> csrf.disable())

                // Request authorization rules
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints (bina login ke accessible)
                        .requestMatchers(
                                "/api/users/register",
                                "/api/users/login",
                                "/actuator/**"  // Health check endpoints
                        ).permitAll()

                        // Sab dusre endpoints ko authentication chahiye
                        .anyRequest().authenticated()
                )

                // Session management - STATELESS (session store nahi karenge)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }

    /**
     * Password Encoder Bean
     *
     * BCrypt - Ye password ko encrypt karta hai
     * Ek hi password ko har baar different encrypt karta hai (salt use karke)
     * Dekryption possible nahi hai, sirf matching check kar sakte hain
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}