module com.project.finnote {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.project.finnote to javafx.fxml;
    exports com.project.finnote;
    exports com.project.finnote.controllers;
    opens com.project.finnote.controllers to javafx.fxml;
    exports com.project.finnote.enums;
    opens com.project.finnote.enums to javafx.fxml;
}