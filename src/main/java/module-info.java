module com.project.finnote {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.project.finnote to javafx.fxml;
    exports com.project.finnote;
    exports com.project.finnote.controllers;
    opens com.project.finnote.controllers to javafx.fxml;
}