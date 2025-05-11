package com.kwizera.javaamalitechlab07hospitalmgtsystem.services;

import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.Patient;

import java.util.List;

public interface PatientService {
    void addPatient(Patient patient);

    Patient getPatient(int id);

    List<Patient> getAllPatients();

    void updatePatient(Patient patient);

    void deletePatient(int id);
}
