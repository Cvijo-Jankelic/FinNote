package com.project.finnote.controllers;

import com.project.finnote.entity.Category;
import com.project.finnote.entity.FinancialRecord;
import com.project.finnote.entity.Notes;
import com.project.finnote.entity.ReportItem;
import com.project.finnote.services.CategoryService;
import com.project.finnote.services.FinancialRecordService;
import com.project.finnote.services.NoteService;
import com.project.finnote.services.ReportService;
import com.project.finnote.utils.PDFExporter;
import com.project.finnote.utils.ProcessExecutor;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class ReportController {
    @FXML private TableView<ReportItem> reportTable;
    @FXML private TableColumn<ReportItem, String> colName;
    @FXML private TableColumn<ReportItem, String> colValue;

    @FXML private TableView<Category> categoriesTable;
    @FXML private TableColumn<Category, Integer> catIdCol;
    @FXML private TableColumn<Category, String> catNameCol;
    @FXML private TableColumn<Category, String> catTypeCol;
    @FXML private TableColumn<Category, String> catCreatedCol;

    @FXML private TableView<Notes> notesTable;
    @FXML private TableColumn<Notes, Integer> noteIdCol;
    @FXML private TableColumn<Notes, Integer> noteUserIdCol;
    @FXML private TableColumn<Notes, String> noteTitleCol;
    @FXML private TableColumn<Notes, String> noteContentCol;
    @FXML private TableColumn<Notes, String> noteCategoryCol;
    @FXML private TableColumn<Notes, String> noteCreatedCol;
    @FXML private TableColumn<Notes, String> noteUpdatedCol;

    @FXML private TableView<FinancialRecord> recordsTable;
    @FXML private TableColumn<FinancialRecord, Integer> recIdCol;
    @FXML private TableColumn<FinancialRecord, Integer> recUserIdCol;
    @FXML private TableColumn<FinancialRecord, String> recAmountCol;
    @FXML private TableColumn<FinancialRecord, String> recCurrencyCol;
    @FXML private TableColumn<FinancialRecord, String> recCategoryCol;
    @FXML private TableColumn<FinancialRecord, String> recNoteCol;
    @FXML private TableColumn<FinancialRecord, String> recCreatedCol;

    @FXML private Button btnExportPdf;

    private final CategoryService        categoryService = new CategoryService();
    private final NoteService           notesService    = new NoteService();
    private final FinancialRecordService recordService   = new FinancialRecordService();
    private final ReportService          reportService   = new ReportService();
    private final PDFExporter            pdfExporter     = new PDFExporter();

    @FXML
    public void initialize() {
        // Metrike
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        // Kategorije kolone
        catIdCol.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        catNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        catTypeCol.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getType().name())
        );
        catCreatedCol.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getCreatedAt().toString())
        );
        // Bilješke kolone
        noteIdCol.setCellValueFactory(new PropertyValueFactory<>("noteId"));
        noteUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        noteTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        noteContentCol.setCellValueFactory(new PropertyValueFactory<>("content"));
        noteCategoryCol.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getCategory().getName())
        );
        noteCreatedCol.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getCreatedAt().toString())
        );
        noteUpdatedCol.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getUpdatedAt().toString())
        );
        // Financijski zapisi kolone
        recIdCol.setCellValueFactory(new PropertyValueFactory<>("recordId"));
        recUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        recAmountCol.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getAmount().toPlainString())
        );
        recCurrencyCol.setCellValueFactory(new PropertyValueFactory<>("currency"));
        recCategoryCol.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getCategory().getName())
        );
        recNoteCol.setCellValueFactory(new PropertyValueFactory<>("note"));
        recCreatedCol.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getCreatedAt().toString())
        );

        // Asinkrono punjenje tablica
        ProcessExecutor.run(
                () -> reportService.generateReport(),
                items -> reportTable.setItems(FXCollections.observableArrayList(items)),
                err -> showError("Greška pri učitavanju metrika", err)
        );
        ProcessExecutor.run(
                () -> categoryService.findAll(),
                cats -> categoriesTable.setItems(FXCollections.observableArrayList(cats)),
                err -> showError("Greška pri učitavanju kategorija", err)
        );
        ProcessExecutor.run(
                () -> notesService.findAll(),
                notes -> notesTable.setItems(FXCollections.observableArrayList(notes)),
                err -> showError("Greška pri učitavanju bilješki", err)
        );
        ProcessExecutor.run(
                () -> recordService.findAll(),
                recs -> recordsTable.setItems(FXCollections.observableArrayList(recs)),
                err -> showError("Greška pri učitavanju zapisa", err)
        );

        btnExportPdf.setOnAction(e -> exportPdf());
    }

    @FXML
    private void exportPdf() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Spremi izvještaj kao PDF");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF files", "*.pdf")
        );
        File file = chooser.showSaveDialog(getStage());
        if (file == null) return;

        ProcessExecutor.run(
                () -> {
                    pdfExporter.exportFullReport(
                            categoryService.findAll(),
                            notesService.findAll(),
                            recordService.findAll(),
                            file.getAbsolutePath()
                    );
                    return file.getAbsolutePath();
                },
                path -> showInfo("Izvoz završen", "PDF je spremljen na: " + path),
                err -> showError("Greška pri izvoz u PDF", err)
        );
    }

    private void showError(String title, Throwable err) {
        err.printStackTrace();
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText(title);
        a.setContentText(err.getClass().getSimpleName() + ": " + err.getMessage());
        a.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Stage getStage() {
        return (Stage) reportTable.getScene().getWindow();
    }
}
