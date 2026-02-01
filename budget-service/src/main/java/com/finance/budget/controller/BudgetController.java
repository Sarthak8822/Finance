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

    /**
     * Delete Budget by ID
     *
     * DELETE /api/budgets/{budgetId}
     * Header: Authorization: Bearer <token>
     */
    @DeleteMapping("/{budgetId}")
    public ResponseEntity<?> deleteBudget(@PathVariable Long budgetId) {
        try {
            budgetService.deleteBudget(budgetId);
            return ResponseEntity.ok(new ApiResponse(
                    true,
                    "Budget deleted successfully!"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    /**
     * Delete All Budgets by User ID
     *
     * DELETE /api/budgets/user/{userId}/all
     * Header: Authorization: Bearer <token>
     */
    @DeleteMapping("/user/{userId}/all")
    public ResponseEntity<?> deleteAllBudgetsByUser(@PathVariable Long userId) {
        try {
            budgetService.deleteAllBudgetsByUser(userId);
            return ResponseEntity.ok(new ApiResponse(
                    true,
                    "All budgets deleted successfully!"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    /**
     * Delete Budget by Category
     *
     * DELETE /api/budgets/user/{userId}/category/{category}
     * Header: Authorization: Bearer <token>
     */
    @DeleteMapping("/user/{userId}/category/{category}")
    public ResponseEntity<?> deleteBudgetByCategory(
            @PathVariable Long userId,
            @PathVariable String category) {
        try {
            budgetService.deleteBudgetByCategory(userId, category);
            return ResponseEntity.ok(new ApiResponse(
                    true,
                    "Budget for category '" + category + "' deleted successfully!"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Budget Service is running!");
    }

    /**
     * API Response Helper Class
     */
    private static class ApiResponse {
        private Boolean success;
        private String message;

        public ApiResponse(Boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public Boolean getSuccess() { return success; }
        public String getMessage() { return message; }
    }
}