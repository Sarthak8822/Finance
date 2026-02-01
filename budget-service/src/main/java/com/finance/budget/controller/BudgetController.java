package com.finance.budget.controller;

import com.finance.budget.dto.*;
import com.finance.budget.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    public ResponseEntity<BudgetResponse> createBudget(@RequestBody BudgetRequest request) {
        BudgetResponse response = budgetService.createBudget(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BudgetResponse>> getAllBudgets(@PathVariable Long userId) {
        List<BudgetResponse> budgets = budgetService.getAllBudgets(userId);
        return ResponseEntity.ok(budgets);
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Budget Service is running!");
    }
}