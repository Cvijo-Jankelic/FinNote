package com.project.finnote.entity;

import com.project.finnote.enums.TypeCategoryEnum;

import java.time.LocalDateTime;

public class Category {
    private Integer categoryId;
    private String name;
    private TypeCategoryEnum type;
    private LocalDateTime createdAt;

    public Category(Integer categoryId, String name, TypeCategoryEnum type, LocalDateTime createdAt) {

        this.categoryId = categoryId;
        this.name = name;
        this.type = type;
        this.createdAt = createdAt;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeCategoryEnum getType() {
        return type;
    }

    public void setType(TypeCategoryEnum type) {
        this.type = type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", createdAt=" + createdAt +
                '}';
    }
}
