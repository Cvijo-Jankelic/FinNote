package com.project.finnote.builders;

import com.project.finnote.entity.Category;
import com.project.finnote.entity.FinancialRecord;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FinancialRecordBuilder {
    private Integer recordId;
    private Integer userId;
    private BigDecimal amount;
    private String currency;
    private Category category;
    private String note;
    private LocalDateTime createdAt;

    public FinancialRecordBuilder setRecordId(Integer recordId) {
        this.recordId = recordId;
        return this;
    }

    public FinancialRecordBuilder setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public FinancialRecordBuilder setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public FinancialRecordBuilder setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public FinancialRecordBuilder setCategory(Category category) {
        this.category = category;
        return this;
    }

    public FinancialRecordBuilder setNote(String note) {
        this.note = note;
        return this;
    }

    public FinancialRecordBuilder setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public FinancialRecord createFinancialRecord() {
        return new FinancialRecord(recordId, userId, amount, currency, category, note, createdAt);
    }
}