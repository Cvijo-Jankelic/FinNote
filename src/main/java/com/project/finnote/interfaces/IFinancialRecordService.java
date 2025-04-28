package com.project.finnote.interfaces;

import com.project.finnote.entity.FinancialRecord;

import java.math.BigDecimal;
import java.util.List;

public interface IFinancialRecordService {
    List<FinancialRecord> findAll();

    /** Dodaje novi financijski zapis i vraća spremljeni objekt. */
    FinancialRecord addRecord(BigDecimal amount, String currency, int categoryId, String note);

}
