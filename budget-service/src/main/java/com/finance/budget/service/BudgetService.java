package com.finance.budget.service;

import com.finance.budget.dto.*;
import com.finance.budget.model.*;
import com.finance.budget.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;

    public BudgetResponse createBudget(BudgetRequest request) {
        Budget budget = new Budget();
        budget.setUserId(request.getUserId());
        budget.setCategory(request.getCategory());
        budget.setBudgetAmount(request.getBudgetAmount());
        budget.setStartDate(request.getStartDate());
        budget.setEndDate(request.getEndDate());
        budget.setPeriod(BudgetPeriod.valueOf(request.getPeriod().toUpperCase()));
        budget.setSpentAmount(BigDecimal.ZERO);

        Budget saved = budgetRepository.save(budget);
        return convertToResponse(saved);
    }

    public List<BudgetResponse> getAllBudgets(Long userId) {
        return budgetRepository.findByUserId(userId)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Delete budget by ID
     */
    public void deleteBudget(Long budgetId) {
        if (!budgetRepository.existsById(budgetId)) {
            throw new RuntimeException("Budget not found with id: " + budgetId);
        }
        budgetRepository.deleteById(budgetId);
    }

    /**
     * Delete all budgets for a user
     */
    public void deleteAllBudgetsByUser(Long userId) {
        List<Budget> budgets = budgetRepository.findByUserId(userId);
        if (budgets.isEmpty()) {
            throw new RuntimeException("No budgets found for user id: " + userId);
        }
        budgetRepository.deleteAll(budgets);
    }

    /**
     * Delete budget by category for a specific user
     */
    public void deleteBudgetByCategory(Long userId, String category) {
        List<Budget> budgets = budgetRepository.findByUserIdAndCategory(userId, category);
        if (budgets.isEmpty()) {
            throw new RuntimeException("No budget found for category: " + category);
        }
        budgetRepository.deleteAll(budgets);
    }

    private BudgetResponse convertToResponse(Budget budget) {
        BigDecimal remaining = budget.getBudgetAmount().subtract(budget.getSpentAmount());
        String status = getStatus(budget.getSpentAmount(), budget.getBudgetAmount());

        return new BudgetResponse(
                budget.getId(),
                budget.getUserId(),
                budget.getCategory(),
                budget.getBudgetAmount(),
                budget.getSpentAmount(),
                remaining,
                budget.getStartDate(),
                budget.getEndDate(),
                status
        );
    }

    private String getStatus(BigDecimal spent, BigDecimal budget) {
        double percentage = spent.divide(budget, 2, BigDecimal.ROUND_HALF_UP)
                .multiply(new BigDecimal(100))
                .doubleValue();

        if (percentage >= 100) return "EXCEEDED";
        if (percentage >= 80) return "WARNING";
        return "SAFE";
    }
}