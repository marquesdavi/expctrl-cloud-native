package com.financial.feature.budget.service;

import com.financial.feature.budget.dto.BudgetDTO;
import com.financial.feature.budget.entity.Budget;
import com.financial.feature.budget.repository.BudgetRepository;
import com.financial.feature.budget.service.contract.BudgetServiceContract;
import com.financial.feature.category.entity.Category;
import com.financial.feature.user.service.contract.UserServiceContract;
import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.List;

@RequestScoped
@RequiredArgsConstructor
public class BudgetService implements BudgetServiceContract {
    private final BudgetRepository budgetRepository;
    private final UserServiceContract userService;

    @Override
    public List<BudgetDTO> list() {
        return budgetRepository.listAll().stream()
                .map(b -> new BudgetDTO(
                        b.id,
                        b.user.id,
                        b.category.id,
                        b.periodStart,
                        b.periodEnd,
                        b.amount
                ))
                .toList();
    }

    @Override
    public BudgetDTO get(Long id) {
        Budget b = findById(id);
        return new BudgetDTO(
                b.id,
                b.user.id,
                b.category.id,
                b.periodStart,
                b.periodEnd,
                b.amount
        );
    }

    @Override
    @Transactional
    public Response create(BudgetDTO dto) {
        var b = new Budget();
        b.user        = userService.findByID(dto.userId());
        b.category    = (Category) Category.findById(dto.categoryId());
        b.periodStart = dto.periodStart();
        b.periodEnd   = dto.periodEnd();
        b.amount      = dto.amount();
        budgetRepository.persist(b);
        return(Response.created(URI.create("/budgets/" + b.id)).build());
    }

    @Override
    @Transactional
    public BudgetDTO update(Long id, BudgetDTO dto) {
        Budget b = findById(id);
        b.user        = userService.findByID(dto.userId());
        b.category    = (Category) Category.findById(dto.categoryId());
        b.periodStart = dto.periodStart();
        b.periodEnd   = dto.periodEnd();
        b.amount      = dto.amount();
        budgetRepository.persist(b);
        return new BudgetDTO(
                b.id,
                b.user.id,
                b.category.id,
                b.periodStart,
                b.periodEnd,
                b.amount
        );
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Budget budget = findById(id);
        budgetRepository.delete(budget);
    }

    @Override
    public Budget findById(Long id){
        return budgetRepository.findByIdOptional(id)
                .orElseThrow(NotFoundException::new);
    }
}
