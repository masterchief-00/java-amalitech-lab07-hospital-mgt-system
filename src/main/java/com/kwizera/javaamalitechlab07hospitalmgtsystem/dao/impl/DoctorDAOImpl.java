package com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.impl;

import com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.DoctorDAO;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.EmployeeDAO;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.Doctor;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.Employee;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.utils.CustomLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAOImpl implements DoctorDAO {
    private final Connection connection;
    private EmployeeDAO employeeDAO;

    public DoctorDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Doctor findById(int id) {
        Doctor doctor = null;
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM doctor WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                doctor = new Doctor();
                Employee employee = employeeDAO.findById(rs.getInt("employee_id"));
                doctor.setId(rs.getInt("id"));
                doctor.setSpeciality(rs.getString("speciality"));
                doctor.setEmployee(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to find doctor by id. SQLException");
            throw new RuntimeException("Unable to find doctor by id. " + e.getMessage(), e);
        }

        return doctor;
    }

    @Override
    public List<Doctor> findAll() {
        List<Doctor> doctors = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM doctor");
            while (rs.next()) {
                Doctor doctor = new Doctor();
                Employee employee = employeeDAO.findById(rs.getInt("employee_id"));
                doctor.setId(rs.getInt("id"));
                doctor.setSpeciality(rs.getString("speciality"));
                doctor.setEmployee(employee);
                doctors.add(doctor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to find doctors. SQLException");
            throw new RuntimeException("Unable to find doctors. " + e.getMessage(), e);
        }
        return doctors;
    }

    @Override
    public void save(Doctor doctor) {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO doctor (speciality,employee_id) VALUES (?,?)")) {
            stmt.setString(1, doctor.getSpeciality());
            stmt.setInt(2, doctor.getEmployee().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to save doctor. SQLException");
            throw new RuntimeException("Unable to save doctor. " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Doctor doctor) {
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE doctor SET speciality = ?, employee_id= ? WHERE id = ?")) {
            stmt.setString(1, doctor.getSpeciality());
            stmt.setInt(2, doctor.getEmployee().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to update doctor. SQLException");
            throw new RuntimeException("Unable to update doctor. " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM doctor WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to delete doctor. SQLException");
            throw new RuntimeException("Unable to delete doctor. " + e.getMessage(), e);
        }
    }
}
