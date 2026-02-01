package com.finance.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * User Entity
 *
 * Ye class ek user ko represent karti hai.
 * Isme user ki saari details store hoti hain.
 *
 * @Entity - Ye batata hai ki ye class ek database table hai
 * @Table - Table ka naam specify karta hai
 * @Data - Lombok annotation (getters, setters, toString automatically ban jate hain)
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * Primary Key
     * @Id - Primary key hai
     * @GeneratedValue - Automatically generate hogi (1, 2, 3...)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Username - Unique hona chahiye
     * @Column - Column ki properties define karta hai
     */
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    /**
     * Email - Unique hona chahiye
     */
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    /**
     * Password - Encrypted form mein store hoga
     */
    @Column(nullable = false)
    private String password;

    /**
     * Full Name
     */
    @Column(length = 100)
    private String fullName;

    /**
     * Phone Number
     */
    @Column(length = 15)
    private String phoneNumber;

    /**
     * Account Status
     * true = active, false = inactive
     */
    @Column(nullable = false)
    private Boolean isActive = true;

    /**
     * Creation Timestamp
     * @CreationTimestamp - Jab record create hoga, automatically time set ho jayega
     */
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Update Timestamp
     * @UpdateTimestamp - Jab bhi record update hoga, time update ho jayega
     */
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}