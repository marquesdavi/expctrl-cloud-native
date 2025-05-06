package com.financial.feature.budget;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BudgetDTO(Long id,
                        Long userId,
                        Long categoryId,
                        LocalDate periodStart,
                        LocalDate periodEnd,
                        BigDecimal amount) {

}
