package com.kwizera.javaamalitechlab07hospitalmgtsystem.dao;

import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.Department;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.Doctor;

import java.util.List;

public interface DepartmentDAO {
    Department findById(int id);

    List<Department> findAll();

    void save(Department department);

    void update(Department department);

    void delete(int id);
}
