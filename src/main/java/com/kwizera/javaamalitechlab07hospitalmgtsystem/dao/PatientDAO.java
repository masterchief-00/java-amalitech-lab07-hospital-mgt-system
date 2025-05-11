package com.kwizera.javaamalitechlab07hospitalmgtsystem.dao;

import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.Patient;

import java.util.List;

public interface PatientDAO {
    Patient findById(int id);

    List<Patient> findAll();

    void save(Patient employee);

    void update(Patient employee);

    void delete(int id);
}
