package com.financial.feature.category.service;

import com.financial.feature.category.dto.CategoryDTO;
import com.financial.feature.category.entity.Category;
import com.financial.feature.category.repository.CategoryRepository;
import com.financial.feature.category.service.contract.CategoryServiceContract;
import com.financial.feature.user.entity.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.List;

@RequestScoped
@RequiredArgsConstructor
public class CategoryService implements CategoryServiceContract {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDTO> list() {
        return categoryRepository.listAll().stream()
                .map(c -> new CategoryDTO(
                        c.id,
                        c.user.id,
                        c.name,
                        c.type,
                        c.parentCategory != null ? c.parentCategory.id : null
                ))
                .toList();
    }

    @Override
    public CategoryDTO get(Long id) {
        Category c = findById(id);
        return new CategoryDTO(
                c.id,
                c.user.id,
                c.name,
                c.type,
                c.parentCategory != null ? c.parentCategory.id : null
        );
    }

    @Override
    @Transactional
    public Response create(CategoryDTO dto) {
        Category c = new Category();
        c.user = (User) User.findById(dto.userId());
        c.name = dto.name();
        c.type = dto.type();
        c.parentCategory = dto.parentCategoryId() != null
                ? categoryRepository.findById(dto.parentCategoryId())
                : null;
        categoryRepository.persist(c);
        return Response.created(URI.create("/categories/" + c.id)).build();
    }

    @Override
    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {
        Category c = findById(id);
        c.user = (User) User.findById(dto.userId());
        c.name = dto.name();
        c.type = dto.type();
        c.parentCategory = dto.parentCategoryId() != null
                ? categoryRepository.findById(dto.parentCategoryId())
                : null;
        return new CategoryDTO(
                c.id,
                c.user.id,
                c.name,
                c.type,
                c.parentCategory != null ? c.parentCategory.id : null
        );
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Category category = findById(id);
        categoryRepository.delete(category);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findByIdOptional(id)
                .orElseThrow(NotFoundException::new);
    }
}
