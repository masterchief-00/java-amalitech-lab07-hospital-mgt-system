module com.kwizera.javaamalitechlab07hospitalmgtsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.kwizera.javaamalitechlab07hospitalmgtsystem to javafx.fxml;
    opens com.kwizera.javaamalitechlab07hospitalmgtsystem.controllers to javafx.fxml;
    opens com.kwizera.javaamalitechlab07hospitalmgtsystem.models to javafx.base;
    exports com.kwizera.javaamalitechlab07hospitalmgtsystem;
}