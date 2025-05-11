package com.kwizera.javaamalitechlab07hospitalmgtsystem.dao;

import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.Diagnostic;

import java.util.List;

public interface DiagnosticDAO {
    Diagnostic findById(int id);

    List<Diagnostic> findAll();

    void save(Diagnostic employee);

    void update(Diagnostic employee);

    void delete(int id);
}
