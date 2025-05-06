package com.financial.feature.category;

public record CategoryDTO(Long id,
                          Long userId,
                          String name,
                          String type,
                          Long parentCategoryId) {
}
