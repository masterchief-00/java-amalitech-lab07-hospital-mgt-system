module com.kwizera.javaamalitechlab07hospitalmgtsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.kwizera.javaamalitechlab07hospitalmgtsystem to javafx.fxml;
    exports com.kwizera.javaamalitechlab07hospitalmgtsystem;
}