package com.finance.user.controller;

import com.finance.user.config.JwtUtil;
import com.finance.user.dto.*;

import com.finance.user.model.User;
import com.finance.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * User Controller
 *
 * Ye class REST API endpoints define karti hai.
 * Client (frontend/mobile app) in endpoints ko call karega.
 *
 * @RestController - Ye batata hai ki ye REST API controller hai
 * @RequestMapping - Base URL path
 * @RequiredArgsConstructor - Lombok (constructor injection)
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    /**
     * Register New User
     *
     * POST /api/users/register
     * Body: { "username": "john", "email": "john@example.com", "password": "pass123" }
     *
     * @Valid - Request body ko validate karega
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest request) {
        try {
            UserResponse user = userService.registerUser(request);
            ApiResponse response = new ApiResponse(
                    true,
                    "User registered successfully!",
                    user
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * User Login
     *
     * POST /api/users/login
     * Body: { "usernameOrEmail": "john", "password": "pass123" }
     * Response: JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest request) {
        try {
            // Authenticate user
            User user = userService.authenticateUser(
                    request.getUsernameOrEmail(),
                    request.getPassword()
            );

            // Generate JWT token
            String token = jwtUtil.generateToken(user.getUsername());

            // Prepare response
            UserResponse userResponse = new UserResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getFullName(),
                    user.getPhoneNumber(),
                    user.getIsActive()
            );

            AuthResponse authResponse = new AuthResponse(token, userResponse);

            return ResponseEntity.ok(new ApiResponse(
                    true,
                    "Login successful!",
                    authResponse
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    /**
     * Get User Profile by ID
     *
     * GET /api/users/{userId}
     * Header: Authorization: Bearer <token>
     */
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable Long userId) {
        try {
            UserResponse user = userService.getUserById(userId);
            return ResponseEntity.ok(new ApiResponse(true, "User found!", user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    /**
     * Get User Profile by Username
     *
     * GET /api/users/username/{username}
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        try {
            UserResponse user = userService.getUserByUsername(username);
            return ResponseEntity.ok(new ApiResponse(true, "User found!", user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    /**
     * Update User Profile
     *
     * PUT /api/users/{userId}
     * Header: Authorization: Bearer <token>
     */
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long userId,
            @RequestBody RegisterRequest request) {
        try {
            UserResponse user = userService.updateUser(userId, request);
            return ResponseEntity.ok(new ApiResponse(
                    true,
                    "User updated successfully!",
                    user
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    /**
     * Health Check Endpoint
     *
     * GET /api/users/health
     */
    @GetMapping("/health")
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok(new ApiResponse(
                true,
                "User Service is running!"
        ));
    }
}