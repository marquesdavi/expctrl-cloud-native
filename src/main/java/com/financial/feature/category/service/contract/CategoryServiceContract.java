package com.financial.feature.category.service.contract;

import com.financial.feature.budget.dto.BudgetDTO;
import com.financial.feature.budget.entity.Budget;
import com.financial.feature.category.dto.CategoryDTO;
import com.financial.feature.category.entity.Category;
import jakarta.ws.rs.core.Response;

import java.util.List;

public interface CategoryServiceContract {
    List<CategoryDTO> list();
    CategoryDTO get(Long id);
    Response create(CategoryDTO dto);
    CategoryDTO update(Long id, CategoryDTO dto);
    void delete(Long id);
    Category findById(Long id);
}
