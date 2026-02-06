package com.finance.budget.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetRequest {
    private Long userId;
    private String category;
    private BigDecimal budgetAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private String period;
    private BigDecimal spentAmount;
}