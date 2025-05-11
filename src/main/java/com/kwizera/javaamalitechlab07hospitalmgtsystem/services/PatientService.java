package com.kwizera.javaamalitechlab07hospitalmgtsystem.services;

import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.Patient;
import javafx.collections.ObservableList;

import java.util.List;

public interface PatientService {
    void addPatient(Patient patient);

    Patient getPatient(int id);

    List<Patient> getAllPatients();

    ObservableList<Patient> getPatientsList();

    List<Patient> getPatientsByNameList(String name);

    void updatePatient(Patient patient);

    void deletePatient(Patient patient);
}
