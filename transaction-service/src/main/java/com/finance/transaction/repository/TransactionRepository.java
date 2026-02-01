package com.finance.transaction.repository;

import com.finance.transaction.model.Transaction;
import com.finance.transaction.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Transaction Repository
 *
 * Custom queries using JPQL (Java Persistence Query Language)
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Find all transactions by user ID
    List<Transaction> findByUserId(Long userId);

    // Find transactions by user and type
    List<Transaction> findByUserIdAndType(Long userId, TransactionType type);

    // Find transactions by user and category
    List<Transaction> findByUserIdAndCategory(Long userId, String category);

    // Find transactions between dates
    List<Transaction> findByUserIdAndTransactionDateBetween(
            Long userId,
            LocalDate startDate,
            LocalDate endDate
    );

    /**
     * Calculate total income for a user
     * @Query - Custom JPQL query
     */
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t " +
            "WHERE t.userId = :userId AND t.type = 'INCOME'")
    BigDecimal calculateTotalIncome(Long userId);

    /**
     * Calculate total expense for a user
     */
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t " +
            "WHERE t.userId = :userId AND t.type = 'EXPENSE'")
    BigDecimal calculateTotalExpense(Long userId);

    /**
     * Get transaction count by category
     */
    @Query("SELECT t.category, COUNT(t) FROM Transaction t " +
            "WHERE t.userId = :userId GROUP BY t.category")
    List<Object[]> getTransactionCountByCategory(Long userId);
}