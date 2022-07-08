module com.example.dbassignment {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.jfoenix;


    opens com.clown.dbassignment to javafx.fxml;
    exports com.clown.dbassignment;
}