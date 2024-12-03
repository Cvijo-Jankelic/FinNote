package com.project.finnote.builders;

import com.project.finnote.entity.Category;
import com.project.finnote.enums.TypeCategoryEnum;

import java.time.LocalDateTime;

public class CategoryBuilder {
    private Integer categoryId;
    private String name;
    private TypeCategoryEnum type;
    private LocalDateTime createdAt;

    public CategoryBuilder setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public CategoryBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CategoryBuilder setType(TypeCategoryEnum type) {
        this.type = type;
        return this;
    }

    public CategoryBuilder setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Category createCategory() {
        return new Category(categoryId, name, type, createdAt);
    }
}