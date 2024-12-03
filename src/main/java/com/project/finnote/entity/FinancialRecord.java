package com.project.finnote.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FinancialRecord {
    private Integer recordId;
    private Integer userId;
    private BigDecimal amount;
    private String currency;
    private Category category;
    private String note;
    private LocalDateTime createdAt;

    public FinancialRecord(Integer recordId, Integer userId, BigDecimal amount, String currency, Category category, String note, LocalDateTime createdAt) {
        this.recordId = recordId;
        this.userId = userId;
        this.amount = amount;
        this.currency = currency;
        this.category = category;
        this.note = note;
        this.createdAt = createdAt;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "FinancialRecord{" +
                "recordId=" + recordId +
                ", userId=" + userId +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", category=" + category +
                ", note='" + note + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
