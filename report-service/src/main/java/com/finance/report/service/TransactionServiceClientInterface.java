package com.finance.report.service;

import com.finance.report.dto.TransactionSummary;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Feign Client - Transaction Service se data lene ke liye
 *
 * Ye automatically Transaction Service ko REST call karega
 * Eureka se service ka address dhoondh lega
 */
@FeignClient(name = "transaction-service")
public interface TransactionServiceClientInterface {

    @GetMapping("/api/transactions/user/{userId}/summary")
    TransactionSummary getTransactionSummary(@PathVariable("userId") Long userId);
}