package com.kwizera.javaamalitechlab07hospitalmgtsystem.dao;

import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.Doctor;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.Nurse;

import java.util.List;

public interface NurseDAO {
    Nurse findById(int id);

    List<Nurse> findAll();

    void save(Nurse nurse);

    void update(Nurse nurse);

    void delete(int id);
}
