package com.financial.feature.dashboard.service.contract;

import com.financial.feature.dashboard.dto.CurrentTotalsDTO;
import com.financial.feature.dashboard.dto.DashboardMetricsDTO;
import com.financial.feature.dashboard.dto.TimeSeriesPointDTO;

import java.util.List;

public interface DashboardServiceContract {
    List<TimeSeriesPointDTO> monthlySummary();
    List<TimeSeriesPointDTO> annualSummary();
    CurrentTotalsDTO currentTotals();
    DashboardMetricsDTO getMetrics();
}
