package com.kwizera.javaamalitechlab07hospitalmgtsystem.dao;

import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.Doctor;

import java.util.List;

public interface DoctorDAO {
    Doctor findById(int id);

    List<Doctor> findAll();

    void save(Doctor doctor);

    void update(Doctor doctor);

    void delete(int id);
}
