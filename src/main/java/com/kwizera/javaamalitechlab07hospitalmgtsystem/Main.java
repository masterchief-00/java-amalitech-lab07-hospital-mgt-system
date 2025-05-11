package com.kwizera.javaamalitechlab07hospitalmgtsystem;

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
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("x.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        try (Connection connection = DBConnection.getConnection()) {
            DBInit.runSchemaSetup(connection);
        } catch (SQLException e) {

        }

        launch();
    }
}