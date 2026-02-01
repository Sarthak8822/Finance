package com.finance.transaction.controller;

import com.finance.transaction.dto.*;
import com.finance.transaction.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> createTransaction(@Valid @RequestBody TransactionRequest request) {
        try {
            TransactionResponse response = transactionService.createTransaction(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TransactionResponse>> getAllTransactions(@PathVariable Long userId) {
        List<TransactionResponse> transactions = transactionService.getAllTransactions(userId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/user/{userId}/type/{type}")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByType(
            @PathVariable Long userId,
            @PathVariable String type) {
        List<TransactionResponse> transactions =
                transactionService.getTransactionsByType(userId, type);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/user/{userId}/summary")
    public ResponseEntity<TransactionSummary> getTransactionSummary(@PathVariable Long userId) {
        TransactionSummary summary = transactionService.getTransactionSummary(userId);
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/user/{userId}/date-range")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByDateRange(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<TransactionResponse> transactions =
                transactionService.getTransactionsByDateRange(userId, startDate, endDate);
        return ResponseEntity.ok(transactions);
    }

    /**
     * Delete Transaction by ID
     *
     * DELETE /api/transactions/{transactionId}
     * Header: Authorization: Bearer <token>
     */
    @DeleteMapping("/{transactionId}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long transactionId) {
        try {
            transactionService.deleteTransaction(transactionId);
            return ResponseEntity.ok(new ApiResponse(
                    true,
                    "Transaction deleted successfully!"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    /**
     * Delete All Transactions by User ID
     *
     * DELETE /api/transactions/user/{userId}/all
     * Header: Authorization: Bearer <token>
     * CAUTION: Ye sabhi transactions delete kar dega!
     */
    @DeleteMapping("/user/{userId}/all")
    public ResponseEntity<?> deleteAllTransactionsByUser(@PathVariable Long userId) {
        try {
            transactionService.deleteAllTransactionsByUser(userId);
            return ResponseEntity.ok(new ApiResponse(
                    true,
                    "All transactions deleted successfully!"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    /**
     * Delete Transactions by Category
     *
     * DELETE /api/transactions/user/{userId}/category/{category}
     * Header: Authorization: Bearer <token>
     */
    @DeleteMapping("/user/{userId}/category/{category}")
    public ResponseEntity<?> deleteTransactionsByCategory(
            @PathVariable Long userId,
            @PathVariable String category) {
        try {
            transactionService.deleteTransactionsByCategory(userId, category);
            return ResponseEntity.ok(new ApiResponse(
                    true,
                    "Transactions in category '" + category + "' deleted successfully!"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Transaction Service is running!");
    }

    /**
     * API Response Helper Class
     */
    private static class ApiResponse {
        private Boolean success;
        private String message;
        private Object data;

        public ApiResponse(Boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public ApiResponse(Boolean success, String message, Object data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }

        public Boolean getSuccess() { return success; }
        public String getMessage() { return message; }
        public Object getData() { return data; }
    }
}