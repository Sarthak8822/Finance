package com.finance.budget.repository;

import com.finance.budget.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByUserId(Long userId);
    List<Budget> findByUserIdAndCategory(Long userId, String category);
    Budget findByBudgetId(Long budgetId);
}