package com.financial.feature.dashboard.dto;

import java.math.BigDecimal;

public record TimeSeriesPointDTO(
        String period,       // ex. "2025-01" ou "2024"
        BigDecimal income,   // soma de t.amount >= 0
        BigDecimal expense   // soma de -t.amount para t.amount < 0
) {}
