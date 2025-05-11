package com.kwizera.javaamalitechlab07hospitalmgtsystem.dao;

import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.Bed;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.Doctor;

import java.util.List;

public interface BedDAO {
    Bed findById(int id);

    List<Bed> findAll();

    void save(Bed bed);

    void update(Bed bed);

    void delete(int id);
}
