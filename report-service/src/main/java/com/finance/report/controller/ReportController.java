package com.finance.report.controller;

import com.finance.report.dto.MonthlyReport;
import com.finance.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/monthly/{userId}")
    public ResponseEntity<MonthlyReport> getMonthlyReport(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "JANUARY") String month) {
        MonthlyReport report = reportService.getMonthlyReport(userId, month);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Report Service is running!");
    }
}