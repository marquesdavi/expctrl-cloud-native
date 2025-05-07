package com.financial.feature.category.dto;

public record CategoryDTO(Long id,
                          Long userId,
                          String name,
                          String type,
                          Long parentCategoryId) {
}
