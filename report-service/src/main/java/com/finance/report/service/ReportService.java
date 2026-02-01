package com.finance.report.service;

import com.finance.report.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final TransactionServiceClientInterface transactionServiceClient;

    public MonthlyReport getMonthlyReport(Long userId, String month) {
        // Transaction Service se data fetch karo
        TransactionSummary summary = transactionServiceClient.getTransactionSummary(userId);

        // Report banao
        MonthlyReport report = new MonthlyReport();
        report.setMonth(month);
        report.setTotalIncome(summary.getTotalIncome());
        report.setTotalExpense(summary.getTotalExpense());
        report.setBalance(summary.getBalance());
        report.setCategoryWiseExpense(new HashMap<>());

        return report;
    }
}