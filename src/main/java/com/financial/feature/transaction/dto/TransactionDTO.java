package com.financial.feature.transaction.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record TransactionDTO(Long id,
                             Long accountId,
                             LocalDate date,
                             BigDecimal amount,
                             String type,
                             String description,
                             String details,
                             String documentNumber,
                             BigDecimal runningBalance,
                             Long categoryId,
                             Long payeeId,
                             Long importBatchId,
                             List<Long> tagIds) {

}
