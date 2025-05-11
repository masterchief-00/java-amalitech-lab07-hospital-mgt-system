package com.kwizera.javaamalitechlab07hospitalmgtsystem.controllers;

import com.kwizera.javaamalitechlab07hospitalmgtsystem.Session.SessionManager;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.Patient;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.services.PatientService;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.utils.CustomLogger;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.utils.InputValidationUtil;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.utils.MainUtilities;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AddPatientPageController {

    private SessionManager session;
    private PatientService patientService;
    private final MainUtilities mainUtilities = new MainUtilities();
    private InputValidationUtil inputValidationUtil = new InputValidationUtil();

    @FXML
    public TextField firstNameInput;

    @FXML
    public TextField surnameInput;

    @FXML
    public TextField addressInput;

    @FXML
    public TextField phoneInput;

    @FXML
    public DatePicker dobInput;

    @FXML
    public Label firstNameErrorLabel;

    @FXML
    public Label surnameErrorLabel;

    @FXML
    public Label addressErrorLabel;

    @FXML
    public Label telephoneErrorLabel;

    @FXML
    public Label dateOfBirthErrorLabel;

    @FXML
    public Button submitPatientBtn;

    @FXML
    public Button cancelBtn;

    @FXML
    public void onConfirmClicked() {
        // read data from inputs
        String firstName = firstNameInput.getText();
        String surname = surnameInput.getText();
        String address = addressInput.getText();
        String phone = phoneInput.getText();
        LocalDate dob = dobInput.getValue();


        try {
            if (validateInput(firstName, surname, address, phone, dob)) {
                int randomNum = (int) (Math.random() * 900) + 100;
                String patientNum = "PATIENT" + randomNum;
                Patient newPatient = new Patient();

                newPatient.setId(0);
                newPatient.setPatientNo(patientNum);
                newPatient.setFirstName(firstName);
                newPatient.setSurName(surname);
                newPatient.setAddress(address);
                newPatient.setTelephoneNumber(phone);
                newPatient.setDob(dob);

                patientService.addPatient(newPatient);
                mainUtilities.displayConfirmation("Patient registered successfully");
                CustomLogger.log(CustomLogger.LogLevel.INFO, "New patient registered.");
            }
        } catch (RuntimeException e) {
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to create patient. " + e.getMessage());
            mainUtilities.displayError("Unable to create patient.");
        }
    }

    @FXML
    public void onCancelClicked() {
        // closes the window
        CustomLogger.log(CustomLogger.LogLevel.INFO, "Exiting add patient screen.");
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {
        try {
            initializeData();
        } catch (RuntimeException e) {
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to initialize data on the main page. " + e.getMessage());
            mainUtilities.displayError("Unable to initialize data on the main page.");
        }
    }

    private void initializeData() {
        session = SessionManager.getInstance();
        patientService = session.getPatientService();
        CustomLogger.log(CustomLogger.LogLevel.INFO, "Initialized data on the add patient page");
    }

    private boolean validateInput(String firstName, String surname, String address, String phoneNumber, LocalDate dob) {
        if (inputValidationUtil.invalidNames(firstName)) {
            firstNameErrorLabel.setText("Invalid name");
            firstNameErrorLabel.setVisible(true);
            return false;
        } else if (inputValidationUtil.invalidNames(surname)) {
            surnameErrorLabel.setText("Invalid name");
            surnameErrorLabel.setVisible(true);
            return false;
        } else if (inputValidationUtil.invalidAddress(address)) {
            addressErrorLabel.setText("Invalid address");
            addressErrorLabel.setVisible(true);
            return false;
        } else if (inputValidationUtil.invalidPhoneNumber(phoneNumber)) {
            telephoneErrorLabel.setText("Invalid phone number");
            telephoneErrorLabel.setVisible(true);
            return false;
        } else if (dob.isAfter(LocalDate.now())) {
            dateOfBirthErrorLabel.setText("Invalid date of birth");
            dateOfBirthErrorLabel.setVisible(true);
            return false;
        } else {
            firstNameErrorLabel.setVisible(false);
            surnameErrorLabel.setVisible(false);
            addressErrorLabel.setVisible(false);
            telephoneErrorLabel.setVisible(false);
            dateOfBirthErrorLabel.setVisible(false);
            return true;
        }
    }
}
