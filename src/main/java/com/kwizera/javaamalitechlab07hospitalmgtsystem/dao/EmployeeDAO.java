package com.kwizera.javaamalitechlab07hospitalmgtsystem.dao;

import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.Employee;

import java.util.List;

public interface EmployeeDAO {
    Employee findById(int id);

    List<Employee> findAll();

    void save(Employee employee);

    void update(Employee employee);

    void delete(int id);
}
