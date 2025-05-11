package com.kwizera.javaamalitechlab07hospitalmgtsystem;

import com.kwizera.javaamalitechlab07hospitalmgtsystem.utils.CustomLogger;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.utils.DBConnection;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.utils.DBInit;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/kwizera/javaamalitechlab07hospitalmgtsystem/views/main_page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 977, 441);
        stage.setTitle("Patient Hospitalization System");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {

        try (Connection connection = DBConnection.getConnection()) {
            DBInit.runSchemaSetup(connection);
        } catch (SQLException e) {
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to execute database schema");
        }

        launch();
    }
}