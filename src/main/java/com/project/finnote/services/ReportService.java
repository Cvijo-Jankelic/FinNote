package com.project.finnote.services;

import com.project.finnote.entity.ReportItem;
import com.project.finnote.entity.Category;
import com.project.finnote.entity.Notes;
import com.project.finnote.entity.FinancialRecord;
import com.project.finnote.interfaces.IReportService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ReportService implements IReportService {
    private final CategoryService categoryService     = new CategoryService();
    private final NoteService notesService           = new NoteService();
    private final FinancialRecordService recordService = new FinancialRecordService();

    /**
     * Generira osnovne metrike za izvještaj:
     * - Broj kategorija
     * - Broj bilješki
     * - Broj financijskih zapisa
     * - Ukupan iznos svih transakcija
     * - Prosječni iznos transakcije
     */
    @Override
    public List<ReportItem> generateReport() {
        List<ReportItem> reportItems = new ArrayList<>();

        List<Category> categories = categoryService.findAll();
        List<Notes> notes         = notesService.findAll();
        List<FinancialRecord> records = recordService.findAll();

        // Broj stavki
        reportItems.add(new ReportItem("Broj kategorija", String.valueOf(categories.size())));
        reportItems.add(new ReportItem("Broj bilješki", String.valueOf(notes.size())));
        reportItems.add(new ReportItem("Broj financijskih zapisa", String.valueOf(records.size())));

        // Ukupan iznos transakcija
        BigDecimal totalAmount = records.stream()
                .map(FinancialRecord::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        reportItems.add(new ReportItem("Ukupan iznos transakcija", totalAmount.toPlainString()));

        // Prosječni iznos transakcije
        BigDecimal averageAmount = BigDecimal.ZERO;
        if (!records.isEmpty()) {
            averageAmount = totalAmount
                    .divide(new BigDecimal(records.size()), 2, RoundingMode.HALF_UP);
        }
        reportItems.add(new ReportItem("Prosječni iznos transakcije", averageAmount.toPlainString()));

        return reportItems;
    }
}
