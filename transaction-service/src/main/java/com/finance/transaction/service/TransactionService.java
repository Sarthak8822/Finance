package com.finance.transaction.service;

import com.finance.transaction.dto.*;
import com.finance.transaction.model.*;
import com.finance.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Transactional
    public TransactionResponse createTransaction(TransactionRequest request) {
        Transaction transaction = new Transaction();
        transaction.setUserId(request.getUserId());
        transaction.setAmount(request.getAmount());
        transaction.setType(TransactionType.valueOf(request.getType().toUpperCase()));
        transaction.setCategory(request.getCategory());
        transaction.setDescription(request.getDescription());
        transaction.setTransactionDate(request.getTransactionDate());

        if (request.getPaymentMethod() != null) {
            transaction.setPaymentMethod(
                    PaymentMethod.valueOf(request.getPaymentMethod().toUpperCase())
            );
        }

        Transaction saved = transactionRepository.save(transaction);
        return convertToResponse(saved);
    }

    public List<TransactionResponse> getAllTransactions(Long userId) {
        return transactionRepository.findByUserId(userId)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<TransactionResponse> getTransactionsByType(Long userId, String type) {
        TransactionType transactionType = TransactionType.valueOf(type.toUpperCase());
        return transactionRepository.findByUserIdAndType(userId, transactionType)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public TransactionSummary getTransactionSummary(Long userId) {
        BigDecimal totalIncome = transactionRepository.calculateTotalIncome(userId);
        BigDecimal totalExpense = transactionRepository.calculateTotalExpense(userId);
        BigDecimal balance = totalIncome.subtract(totalExpense);
        Long totalTransactions = (long) transactionRepository.findByUserId(userId).size();

        return new TransactionSummary(totalIncome, totalExpense, balance, totalTransactions);
    }

    public List<TransactionResponse> getTransactionsByDateRange(
            Long userId, LocalDate startDate, LocalDate endDate) {
        return transactionRepository
                .findByUserIdAndTransactionDateBetween(userId, startDate, endDate)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteTransaction(Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }

    private TransactionResponse convertToResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getUserId(),
                transaction.getAmount(),
                transaction.getType().name(),
                transaction.getCategory(),
                transaction.getDescription(),
                transaction.getTransactionDate(),
                transaction.getPaymentMethod() != null ?
                        transaction.getPaymentMethod().name() : null
        );
    }
}