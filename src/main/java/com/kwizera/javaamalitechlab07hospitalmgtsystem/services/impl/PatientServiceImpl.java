package com.kwizera.javaamalitechlab07hospitalmgtsystem.services.impl;

import com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.PatientDAO;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.Patient;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.services.PatientService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class PatientServiceImpl implements PatientService {
    private final PatientDAO patientDAO;

    // observable list to allow real time sync with the UI table
    private ObservableList<Patient> patientsList = FXCollections.observableArrayList();

    public PatientServiceImpl(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }


    @Override
    public void addPatient(Patient patient) {
        patientDAO.save(patient);
        patientsList.add(patient);
    }

    @Override
    public Patient getPatient(int id) {
        return patientDAO.findById(id);
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientDAO.findAll();
    }


    @Override
    public void updatePatient(Patient patient) {
        patientDAO.update(patient);
    }

    @Override
    public void deletePatient(Patient patient) {
        patientDAO.delete(patient);
        patientsList.remove(patient);
    }

    @Override
    public ObservableList<Patient> getPatientsList() {
        List<Patient> allPatients = patientDAO.findAll();
        patientsList.setAll(allPatients);
        return patientsList;
    }

    @Override
    public List<Patient> getPatientsByNameList(String name) {
        return patientDAO.findByName(name);
    }
}
