package com.kwizera.javaamalitechlab07hospitalmgtsystem.dao;

import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.Hospitalization;

import java.util.List;

public interface HospitalizationDAO {
    Hospitalization findById(int id);

    List<Hospitalization> findAll();

    void save(Hospitalization employee);

    void update(Hospitalization employee);

    void delete(int id);
}
