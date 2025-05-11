package com.kwizera.javaamalitechlab07hospitalmgtsystem.dao;

import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.Doctor;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.Ward;

import java.util.List;

public interface WardDAO {
    Ward findById(int id);

    List<Ward> findAll();

    void save(Ward ward);

    void update(Ward ward);

    void delete(int id);
}
