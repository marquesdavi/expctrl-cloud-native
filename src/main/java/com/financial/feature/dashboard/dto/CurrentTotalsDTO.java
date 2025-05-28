package com.financial.feature.dashboard.dto;

import java.math.BigDecimal;

public record CurrentTotalsDTO(
        BigDecimal income,   // soma de t.amount >= 0 no mês atual
        BigDecimal expense   // soma de -t.amount para t.amount < 0 no mês atual
) {}
