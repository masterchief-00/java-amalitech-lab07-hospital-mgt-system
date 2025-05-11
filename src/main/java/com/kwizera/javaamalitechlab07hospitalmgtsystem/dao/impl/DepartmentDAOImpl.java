package com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.impl;

import com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.DepartmentDAO;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.DoctorDAO;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.Department;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.Doctor;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.utils.CustomLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAOImpl implements DepartmentDAO {
    private final Connection connection;
    private DoctorDAO doctorDAO;

    public DepartmentDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Department findById(int id) {
        Department department = null;
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM department WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                department = new Department();
                Doctor doctor = doctorDAO.findById(rs.getInt("director_id"));

                department.setId(rs.getInt("id"));
                department.setName(rs.getString("name"));
                department.setDirector(doctor);
            }
        } catch (SQLException e) {
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to find department by id. SQLException");
            throw new RuntimeException("Unable to find department by id. " + e.getMessage(), e);
        }

        return department;
    }

    @Override
    public List<Department> findAll() {
        List<Department> departments = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM department");
            while (rs.next()) {
                Department department = new Department();
                Doctor doctor = doctorDAO.findById(rs.getInt("director_id"));

                department.setId(rs.getInt("id"));
                department.setName(rs.getString("name"));
                department.setDirector(doctor);
                departments.add(department);
            }
        } catch (SQLException e) {
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to find departments. SQLException");
            throw new RuntimeException("Unable to find departments. " + e.getMessage(), e);
        }
        return departments;
    }

    @Override
    public void save(Department department) {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO department (name,director_id) VALUES (?,?)")) {
            stmt.setString(1, department.getName());
            stmt.setInt(2, department.getDirector().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to save department. SQLException");
            throw new RuntimeException("Unable to save department. " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Department department) {
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE department SET name = ?, director_id = ? WHERE id = ?")) {
            stmt.setString(1, department.getName());
            stmt.setInt(2, department.getDirector().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to update department. SQLException");
            throw new RuntimeException("Unable to update department. " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM department WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to delete department. SQLException");
            throw new RuntimeException("Unable to delete department. " + e.getMessage(), e);
        }
    }
}
