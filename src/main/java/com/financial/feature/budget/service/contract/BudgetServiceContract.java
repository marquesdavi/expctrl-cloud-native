package com.financial.feature.budget.service.contract;

import com.financial.feature.bank.dto.BankDTO;
import com.financial.feature.bank.entity.Bank;
import com.financial.feature.budget.dto.BudgetDTO;
import com.financial.feature.budget.entity.Budget;
import jakarta.ws.rs.core.Response;

import java.util.List;

public interface BudgetServiceContract {
    List<BudgetDTO> list();
    BudgetDTO get(Long id);
    Response create(BudgetDTO dto);
    BudgetDTO update(Long id, BudgetDTO dto);
    void delete(Long id);
    Budget findById(Long id);
}
