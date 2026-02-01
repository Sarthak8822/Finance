package com.finance.transaction.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {

    private Long id;
    private Long userId;
    private BigDecimal amount;
    private String type;
    private String category;
    private String description;
    private LocalDate transactionDate;
    private String paymentMethod;
}
