package com.financial.feature.dashboard.resource;

import com.financial.feature.dashboard.dto.CurrentTotalsDTO;
import com.financial.feature.dashboard.dto.DashboardMetricsDTO;
import com.financial.feature.dashboard.dto.TimeSeriesPointDTO;
import com.financial.feature.dashboard.service.contract.DashboardServiceContract;
import io.quarkus.security.Authenticated;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;

import java.util.List;

@Authenticated
@Path("/dashboard")
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class DashboardResource {
    private final DashboardServiceContract svc;

    @GET
    @Path("/monthly")
    public List<TimeSeriesPointDTO> monthly() {
        return svc.monthlySummary();
    }

    @GET
    @Path("/metrics")
    @Operation(summary = "Métricas financeiras mensais e anuais")
    public DashboardMetricsDTO getMetrics() {
        return svc.getMetrics();
    }

    @GET
    @Path("/annual")
    public List<TimeSeriesPointDTO> annual() {
        return svc.annualSummary();
    }

    @GET
    @Path("/current-totals")
    public CurrentTotalsDTO currentTotals() {
        return svc.currentTotals();
    }
}
