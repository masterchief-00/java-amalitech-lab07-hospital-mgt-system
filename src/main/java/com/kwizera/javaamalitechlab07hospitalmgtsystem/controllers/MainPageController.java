package com.kwizera.javaamalitechlab07hospitalmgtsystem.controllers;

import com.kwizera.javaamalitechlab07hospitalmgtsystem.Session.SessionManager;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.Patient;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.services.PatientService;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.utils.CustomLogger;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.utils.InputValidationUtil;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.utils.MainUtilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class MainPageController {
    private SessionManager session;
    private PatientService patientService;
    private final MainUtilities mainUtilities = new MainUtilities();
    private final InputValidationUtil inputValidationUtil = new InputValidationUtil();

    @FXML
    public Button searchBtn;

    @FXML
    public AnchorPane patientDetailsPane;

    @FXML
    public Label noPatientLabel;

    @FXML
    public TextField searchInput;

    @FXML
    public Button addNewBtn;

    @FXML
    public Button resetBtn;

    @FXML
    public TableView<Patient> patientTable;

    @FXML
    public TableColumn<Patient, String> patientNoCol;

    @FXML
    public TableColumn<Patient, String> firstNameCol;

    @FXML
    public TableColumn<Patient, String> surnameCol;

    @FXML
    public TableColumn<Patient, String> phoneCol;

    @FXML
    public TableColumn<Patient, String> addressCol;

    @FXML
    public TableColumn<Patient, String> birthDateCol;

    private ObservableList<Patient> tableData = FXCollections.observableArrayList();

    @FXML
    public void onSearchClicked() {
        try {
            String q = searchInput.getText();

            if (inputValidationUtil.invalidNames(q)) return;

            List<Patient> results = patientService.getPatientsByNameList(q);

            tableData.clear();
            tableData.addAll(results);

        } catch (RuntimeException e) {
            mainUtilities.displayError("Unable to load the search by names");
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to load the search by names. IOException" + e.getMessage());

        }
    }

    @FXML
    public void onAddNewClicked(ActionEvent actionEvent) {
        try {
            mainUtilities.displayModularScene("/com/kwizera/javaamalitechlab07hospitalmgtsystem/views/add_patient_page.fxml", addNewBtn, "Add patient");
        } catch (IOException e) {
            mainUtilities.displayError("Unable to load the add patient page");
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to load the add page page. IOException");
        }
    }

    @FXML
    public void onResetClicked(ActionEvent actionEvent) {
        try {
            patientService.getPatientsList();
        } catch (RuntimeException e) {
            mainUtilities.displayError("Unable to recent patients list");
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to recent patients list " + e.getMessage());
        }
    }

    @FXML
    private void initialize() {
        try {
            initializeData();
            setUpTableColumns();
            setUpListeners();
            loadInitialTableData();
        } catch (RuntimeException e) {
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to initialize data on the main page. " + e.getMessage());
            mainUtilities.displayError("Unable to initialize data on the main page.");
        }
    }

    private void initializeData() {
        session = SessionManager.getInstance();
        patientService = session.getPatientService();
        tableData = patientService.getPatientsList();
        CustomLogger.log(CustomLogger.LogLevel.INFO, "Initialized data on the main page");
    }

    // sets initial configurations for the table columns
    private void setUpTableColumns() {
        // bind columns with the patient class attributes
        patientNoCol.setCellValueFactory(new PropertyValueFactory<>("patientNo"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surName"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("telephoneNumber"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        birthDateCol.setCellValueFactory(new PropertyValueFactory<>("dob"));
    }

    private void loadInitialTableData() {
        patientTable.setItems(tableData);
    }

    private void setUpListeners() {
        patientTable.getSelectionModel().selectedItemProperty().addListener(((obs, oldSelection, selected) -> {
            if (selected != null) {
                updatePatientDetailsPane();
            }
        }));
    }

    // in case there is an update operation refreshes selected employee details
    private void updatePatientDetailsPane() {
        Patient selected = patientTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            patientDetailsPane.getChildren().clear();

            VBox detailsBox = getDetailsBox(selected);

            patientDetailsPane.getChildren().add(detailsBox);
        }
    }

    // vbox to display details of the selected employee from the table view
    private VBox getDetailsBox(Patient selectedPatient) {
        Label patientNo = new Label("No: " + selectedPatient.getPatientNo());
        Label firstName = new Label("First Name: " + selectedPatient.getFirstName());
        Label surname = new Label("Surname: " + selectedPatient.getSurName());
        Label phoneNumber = new Label("Telephone: " + selectedPatient.getTelephoneNumber());
        Label address = new Label("Address: " + selectedPatient.getAddress());
        Button removePatientBtn = getRemovePatientBtn(selectedPatient);

        patientNo.setStyle("-fx-font-weight: bold; -fx-font-size: 15;");
        firstName.setStyle("-fx-font-weight: bold; -fx-font-size: 15;");
        surname.setStyle("-fx-font-weight: bold; -fx-font-size: 15;");
        phoneNumber.setStyle("-fx-font-weight: bold; -fx-font-size: 15;");
        address.setStyle("-fx-font-weight: bold; -fx-font-size: 15;");

        VBox detailsBox = new VBox(10, patientNo, firstName, surname, phoneNumber, address, removePatientBtn);
        detailsBox.setLayoutX(10);
        detailsBox.setLayoutY(10);
        return detailsBox;
    }

    // creating a remove button and adding method to it that remove an employee
    private Button getRemovePatientBtn(Patient selectedPatient) {
        Button removePatientBtn = new Button("Remove this patient");

        removePatientBtn.setOnAction(event -> {
            int removedIndex = patientTable.getSelectionModel().getSelectedIndex();

            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Are you sure you want to delete " + selectedPatient.getFirstName() + "?",
                    ButtonType.YES, ButtonType.NO);
            alert.setHeaderText("Confirm Deletion");

            alert.showAndWait().ifPresent(response -> {

                if (response == ButtonType.YES) {
                    patientService.deletePatient(selectedPatient);
                    mainUtilities.displayConfirmation("Patient deleted");
                    selectNext(removedIndex);
                    removePatientBtn.setDisable(true);
                } else {
                    removePatientBtn.setDisable(false);
                }
            });

        });
        return removePatientBtn;
    }

    // in case an employee was removed, this method selects the next record in the table(if possible)
    private void selectNext(int removedIndex) {
        if (!tableData.isEmpty()) {
            int nextIndex = Math.min(removedIndex, tableData.size() - 1); // stay in bounds
            patientTable.getSelectionModel().select(nextIndex);
        }
    }
}
