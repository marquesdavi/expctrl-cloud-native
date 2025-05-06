package com.financial.dto;

import java.time.Instant;

public record ImportBatchDTO(Long id,
                             Long userId,
                             String source,
                             String fileName,
                             Instant importedAt) {

}
