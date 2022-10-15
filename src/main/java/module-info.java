module com.example.mydictionary {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.mydictionary to javafx.fxml;
    exports com.example.mydictionary;
}