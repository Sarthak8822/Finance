package com.finance.user.repository;

import com.finance.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * User Repository
 *
 * Ye interface database operations ke liye hai.
 * JpaRepository ko extend karne se automatically basic CRUD operations mil jate hain:
 * - save() - Data save karna
 * - findById() - ID se data dhundhna
 * - findAll() - Sab data lana
 * - delete() - Data delete karna
 *
 * Custom methods bhi define kar sakte hain.
 * Spring automatically implementation bana deta hai based on method name!
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Username se user dhundhna
     * Optional - User mil bhi sakta hai aur nahi bhi
     */
    Optional<User> findByUsername(String username);

    /**
     * Email se user dhundhna
     */
    Optional<User> findByEmail(String email);

    /**
     * Username ya email se user dhundhna
     * Method name se Spring samajh jata hai ki kya karna hai
     */
    Optional<User> findByUsernameOrEmail(String username, String email);

    /**
     * Check karna ki username exist karta hai ya nahi
     * Returns: true/false
     */
    Boolean existsByUsername(String username);

    /**
     * Check karna ki email exist karta hai ya nahi
     */
    Boolean existsByEmail(String email);
}