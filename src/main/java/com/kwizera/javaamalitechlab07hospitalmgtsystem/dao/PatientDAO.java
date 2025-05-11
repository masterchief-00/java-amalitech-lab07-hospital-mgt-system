package com.kwizera.javaamalitechlab07hospitalmgtsystem.dao;

import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.Patient;

import java.util.List;

public interface PatientDAO {
    Patient findById(int id);

    List<Patient> findAll();

    List<Patient> findByName(String name);

    void save(Patient patient);

    void update(Patient patient);

    void delete(Patient patient);
}
