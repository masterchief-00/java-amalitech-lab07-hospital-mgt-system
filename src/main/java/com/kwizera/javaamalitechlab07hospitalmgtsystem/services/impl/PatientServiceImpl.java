package com.kwizera.javaamalitechlab07hospitalmgtsystem.services.impl;

import com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.PatientDAO;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.Patient;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.services.PatientService;

import java.util.List;

public class PatientServiceImpl implements PatientService {
    private final PatientDAO patientDAO;

    public PatientServiceImpl(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }


    @Override
    public void addPatient(Patient patient) {
        patientDAO.save(patient);
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
    public void deletePatient(int id) {
        patientDAO.delete(id);
    }
}
