module com.project.finnote {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.project.finnote to javafx.fxml;
    exports com.project.finnote;
}