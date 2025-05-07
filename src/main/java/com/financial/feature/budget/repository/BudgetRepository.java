package com.financial.feature.budget.repository;

import com.financial.feature.budget.entity.Budget;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BudgetRepository implements PanacheRepository<Budget> {

}
