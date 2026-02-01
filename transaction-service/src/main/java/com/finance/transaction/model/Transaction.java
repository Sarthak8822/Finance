package com.finance.transaction.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Transaction Entity
 *
 * Ye class ek financial transaction ko represent karti hai
 * Income ya Expense dono track kar sakte hain
 */
@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * User ID - Kis user ka transaction hai
     * Foreign key to User Service
     */
    @Column(nullable = false)
    private Long userId;

    /**
     * Transaction Amount
     * BigDecimal - Precise decimal calculations ke liye best
     */
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    /**
     * Transaction Type: INCOME ya EXPENSE
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TransactionType type;

    /**
     * Category: Food, Transport, Salary, etc.
     */
    @Column(nullable = false, length = 50)
    private String category;

    /**
     * Description/Note
     */
    @Column(length = 500)
    private String description;

    /**
     * Transaction Date
     */
    @Column(nullable = false)
    private LocalDate transactionDate;

    /**
     * Payment Method
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private PaymentMethod paymentMethod;

    /**
     * Created timestamp
     */
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
