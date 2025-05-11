package com.kwizera.javaamalitechlab07hospitalmgtsystem.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class MainUtilities {
    // a utility to render error messages
    public void displayError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // utility method to render confirmation messages
    public void displayConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Completed");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // displays a modular window
    public void displayModularScene(String fxmlFile, Button sourceButton, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner((Stage) sourceButton.getScene().getWindow());

        stage.setResizable(false);
        stage.setOnShown(e -> {
            Window owner = stage.getOwner();
            stage.setX(owner.getX() + (owner.getWidth() - stage.getWidth()) / 2);
            stage.setY(owner.getY() + (owner.getHeight() - stage.getHeight()) / 3);
        });

        stage.show();
    }

}
