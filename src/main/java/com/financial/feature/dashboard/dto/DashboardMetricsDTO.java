package com.financial.feature.dashboard.dto;

import java.math.BigDecimal;

public record DashboardMetricsDTO(
        BigDecimal monthlyRevenue,
        BigDecimal monthlyExpense,
        BigDecimal yearlyRevenue,
        BigDecimal yearlyExpense
) {}
