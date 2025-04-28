package com.project.finnote.utils;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Paragraph;

import com.project.finnote.entity.Category;
import com.project.finnote.entity.Notes;
import com.project.finnote.entity.FinancialRecord;

import java.util.List;

public class PDFExporter {
    /**
     * Generira PDF s odvojenim sekcijama: Kategorije, Bilješke, Financijski zapisi.
     */
    public void exportFullReport(
            List<Category> categories,
            List<Notes> notes,
            List<FinancialRecord> records,
            String destPath
    ) {
        try (
                PdfWriter writer  = new PdfWriter(destPath);
                PdfDocument pdfDoc = new PdfDocument(writer);
                Document document  = new Document(pdfDoc)
        ) {
            // --- Kategorije ---
            document.add(new Paragraph("=== Kategorije ===\n"));
            float[] catCols = {50F, 150F, 100F, 150F};
            Table catTable = new Table(catCols);
            catTable.addHeaderCell("ID");
            catTable.addHeaderCell("Naziv");
            catTable.addHeaderCell("Tip");
            catTable.addHeaderCell("Kreirano");
            for (Category c : categories) {
                catTable.addCell(c.getCategoryId().toString());
                catTable.addCell(c.getName());
                catTable.addCell(c.getType().name());
                catTable.addCell(c.getCreatedAt().toString());
            }
            document.add(catTable);
            document.add(new Paragraph("\n"));

            // --- Bilješke ---
            document.add(new Paragraph("=== Bilješke ===\n"));
            float[] noteCols = {50F, 80F, 100F, 200F, 100F, 120F, 120F};
            Table noteTable = new Table(noteCols);
            noteTable.addHeaderCell("Note ID");
            noteTable.addHeaderCell("User ID");
            noteTable.addHeaderCell("Title");
            noteTable.addHeaderCell("Content");
            noteTable.addHeaderCell("Category");
            noteTable.addHeaderCell("Created");
            noteTable.addHeaderCell("Updated");
            for (Notes n : notes) {
                noteTable.addCell(n.getNoteId().toString());
                noteTable.addCell(n.getUserId().toString());
                noteTable.addCell(n.getTitle());
                noteTable.addCell(n.getContent());
                noteTable.addCell(n.getCategory().getName());
                noteTable.addCell(n.getCreatedAt().toString());
                noteTable.addCell(n.getUpdatedAt().toString());
            }
            document.add(noteTable);
            document.add(new Paragraph("\n"));

            // --- Financijski zapisi ---
            document.add(new Paragraph("=== Financijski zapisi ===\n"));
            float[] recCols = {50F, 80F, 80F, 60F, 100F, 150F, 120F};
            Table recTable = new Table(recCols);
            recTable.addHeaderCell("Record ID");
            recTable.addHeaderCell("User ID");
            recTable.addHeaderCell("Amount");
            recTable.addHeaderCell("Currency");
            recTable.addHeaderCell("Category");
            recTable.addHeaderCell("Note");
            recTable.addHeaderCell("Created");
            for (FinancialRecord r : records) {
                recTable.addCell(r.getRecordId().toString());
                recTable.addCell(r.getUserId().toString());
                recTable.addCell(r.getAmount().toPlainString());
                recTable.addCell(r.getCurrency());
                recTable.addCell(r.getCategory().getName());
                recTable.addCell(r.getNote());
                recTable.addCell(r.getCreatedAt().toString());
            }
            document.add(recTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
