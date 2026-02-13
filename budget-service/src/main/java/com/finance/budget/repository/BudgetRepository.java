package com.finance.budget.repository;

import com.finance.budget.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByUserId(Long userId);
    List<Budget> findByUserIdAndCategory(Long userId, String category);
    boolean existsById(Long Id);

    @Override
    Optional<Budget> findById(Long id);
}
