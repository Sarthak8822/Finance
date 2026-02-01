package com.finance.user.service;

import com.finance.user.dto.*;
import com.finance.user.model.User;
import com.finance.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User Service
 *
 * Ye class business logic handle karti hai.
 * Controller se request aati hai -> Service process karti hai -> Repository database se interact karti hai
 *
 * @Service - Ye batata hai ki ye ek service class hai
 * @RequiredArgsConstructor - Lombok annotation (constructor automatically ban jata hai)
 * @Transactional - Database operations ko safely handle karta hai
 */
@Service
@RequiredArgsConstructor
public class UserService {

    // Dependencies
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;  // Password encrypt karne ke liye

    /**
     * User Registration
     *
     * Steps:
     * 1. Check karo ki username/email already exist to nahi
     * 2. Password ko encrypt karo
     * 3. User ko database mein save karo
     * 4. Response return karo
     */
    @Transactional
    public UserResponse registerUser(RegisterRequest request) {
        // Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists!");
        }

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists!");
        }

        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));  // Password encrypt
        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setIsActive(true);

        // Save to database
        User savedUser = userRepository.save(user);

        // Convert to response DTO (password nahi bhejte)
        return convertToUserResponse(savedUser);
    }

    /**
     * User Login
     *
     * Steps:
     * 1. User ko username/email se dhundho
     * 2. Password match karo
     * 3. JWT token generate karo
     * 4. Token aur user details return karo
     */
    public User authenticateUser(String usernameOrEmail, String password) {
        // Find user by username or email
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        // Check if password matches
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password!");
        }

        // Check if user is active
        if (!user.getIsActive()) {
            throw new RuntimeException("User account is inactive!");
        }

        return user;
    }

    /**
     * Get User Profile by ID
     */
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        return convertToUserResponse(user);
    }

    /**
     * Get User by Username
     */
    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        return convertToUserResponse(user);
    }

    /**
     * Update User Profile
     */
    @Transactional
    public UserResponse updateUser(Long userId, RegisterRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        // Update fields
        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }
        if (request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }

        User updatedUser = userRepository.save(user);
        return convertToUserResponse(updatedUser);
    }

    /**
     * Delete User (Hard Delete)
     * CAUTION: This will permanently delete the user from database
     */
    @Transactional
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }

    /**
     * Deactivate User (Soft Delete)
     * User data rahega but account inactive ho jayega
     */
    @Transactional
    public void deactivateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        user.setIsActive(false);
        userRepository.save(user);
    }

    /**
     * Reactivate User Account
     */
    @Transactional
    public void reactivateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        user.setIsActive(true);
        userRepository.save(user);
    }

    /**
     * Helper method: Convert User entity to UserResponse DTO
     * Password ko response mein nahi bhejte (security)
     */
    private UserResponse convertToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFullName(user.getFullName());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setIsActive(user.getIsActive());
        return response;
    }
}