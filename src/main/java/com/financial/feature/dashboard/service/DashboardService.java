package com.financial.feature.dashboard.service;

import com.financial.feature.dashboard.dto.CurrentTotalsDTO;
import com.financial.feature.dashboard.dto.DashboardMetricsDTO;
import com.financial.feature.dashboard.dto.TimeSeriesPointDTO;
import com.financial.feature.dashboard.service.contract.DashboardServiceContract;
import com.financial.feature.transaction.repository.TransactionRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RequestScoped
public class DashboardService implements DashboardServiceContract {

    @Inject
    EntityManager em;
    @Inject
    TransactionRepository txRepo;

    @Override
    public List<TimeSeriesPointDTO> monthlySummary() {
        // agrupa por YYYY-MM
        var q = em.createQuery("""
            SELECT new com.financial.feature.dashboard.dto.TimeSeriesPointDTO(
              FUNCTION('TO_CHAR', t.date, 'YYYY-MM'),
              COALESCE(SUM(CASE WHEN t.amount >= 0 THEN t.amount ELSE 0 END),0),
              COALESCE(SUM(CASE WHEN t.amount <  0 THEN -t.amount   ELSE 0 END),0)
            )
            FROM Transaction t
            GROUP BY FUNCTION('TO_CHAR', t.date, 'YYYY-MM')
            ORDER BY FUNCTION('TO_CHAR', t.date, 'YYYY-MM')
            """, TimeSeriesPointDTO.class);
        return q.getResultList();
    }

    @Override
    public List<TimeSeriesPointDTO> annualSummary() {
        // agrupa por YYYY
        var q = em.createQuery("""
            SELECT new com.financial.feature.dashboard.dto.TimeSeriesPointDTO(
              FUNCTION('TO_CHAR', t.date, 'YYYY'),
              COALESCE(SUM(CASE WHEN t.amount >= 0 THEN t.amount ELSE 0 END),0),
              COALESCE(SUM(CASE WHEN t.amount <  0 THEN -t.amount   ELSE 0 END),0)
            )
            FROM Transaction t
            GROUP BY FUNCTION('TO_CHAR', t.date, 'YYYY')
            ORDER BY FUNCTION('TO_CHAR', t.date, 'YYYY')
            """, TimeSeriesPointDTO.class);
        return q.getResultList();
    }

    @Override
    @Transactional
    public CurrentTotalsDTO currentTotals() {
        LocalDate today = LocalDate.now();
        LocalDate firstOfMonth = today.withDayOfMonth(1);

        Object[] sums = (Object[]) em.createQuery("""
            SELECT
              COALESCE(SUM(CASE WHEN t.amount >= 0 THEN t.amount ELSE 0 END),0),
              COALESCE(SUM(CASE WHEN t.amount <  0 THEN -t.amount   ELSE 0 END),0)
            FROM Transaction t
            WHERE t.date BETWEEN :start AND :end
            """)
                .setParameter("start", firstOfMonth)
                .setParameter("end", today)
                .getSingleResult();

        BigDecimal income  = (BigDecimal) sums[0];
        BigDecimal expense = (BigDecimal) sums[1];
        return new CurrentTotalsDTO(income, expense);
    }

    @Override
    public DashboardMetricsDTO getMetrics() {
        LocalDate today = LocalDate.now();
        LocalDate monthStart = today.withDayOfMonth(1);
        LocalDate yearStart  = today.withDayOfYear(1);

        BigDecimal mRev = sumByTypeAndPeriod("credit", monthStart, today);
        BigDecimal mExp = sumByTypeAndPeriod("debit",  monthStart, today);
        BigDecimal yRev = sumByTypeAndPeriod("credit", yearStart,  today);
        BigDecimal yExp = sumByTypeAndPeriod("debit",  yearStart,  today);

        return new DashboardMetricsDTO(mRev, mExp, yRev, yExp);
    }

    private BigDecimal sumByTypeAndPeriod(String type, LocalDate start, LocalDate end) {
        return txRepo.find(
                        "type = ?1 and date between ?2 and ?3",
                        type, start, end
                )
                .stream()
                .map(t -> t.amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
