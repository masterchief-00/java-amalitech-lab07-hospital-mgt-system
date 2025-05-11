package com.kwizera.javaamalitechlab07hospitalmgtsystem.dao;

import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.Admission;

import java.util.List;

public interface AdmissionDAO {
    Admission findById(int id);

    List<Admission> findAll();

    void save(Admission employee);

    void update(Admission employee);

    void delete(int id);
}
