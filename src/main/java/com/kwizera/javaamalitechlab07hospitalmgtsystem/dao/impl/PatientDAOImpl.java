package com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.impl;

import com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.PatientDAO;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.Patient;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.utils.CustomLogger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientDAOImpl implements PatientDAO {
    private final Connection connection;

    public PatientDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Patient findById(int id) {
        Patient patient = null;
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM patient WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                patient = new Patient();
                patient.setId(rs.getInt("id"));
                patient.setPatientNo(rs.getString("patient_no"));
                patient.setFirstName(rs.getString("first_name"));
                patient.setSurName(rs.getString("sur_name"));
                patient.setAddress(rs.getString("address"));
                patient.setTelephoneNumber("telephone_number");
                patient.setDob(rs.getDate("dob").toLocalDate());
            }
        } catch (SQLException e) {
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to find patient by id. SQLException");
            throw new RuntimeException("Unable to find patient by id. " + e.getMessage(), e);
        }

        return patient;
    }

    @Override
    public List<Patient> findAll() {
        List<Patient> patients = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM patient");
            while (rs.next()) {
                Patient patient = new Patient();
                patient.setId(rs.getInt("id"));
                patient.setPatientNo(rs.getString("patient_no"));
                patient.setFirstName(rs.getString("first_name"));
                patient.setSurName(rs.getString("sur_name"));
                patient.setAddress(rs.getString("address"));
                patient.setTelephoneNumber("telephone_number");
                patient.setDob(rs.getDate("dob").toLocalDate());
                patients.add(patient);
            }
        } catch (SQLException e) {
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to find patients. SQLException");
            throw new RuntimeException("Unable to find patients." + e.getMessage(), e);
        }
        return patients;
    }

    @Override
    public void save(Patient patient) {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO patient (patient_no,first_name,sur_name,address,telephone_number,dob) VALUES (?,?,?,?,?)")) {
            stmt.setString(1, patient.getPatientNo());
            stmt.setString(2, patient.getFirstName());
            stmt.setString(3, patient.getSurName());
            stmt.setString(4, patient.getAddress());
            stmt.setString(5, patient.getTelephoneNumber());
            stmt.setDate(6, Date.valueOf(patient.getDob()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to save patient. SQLException");
            throw new RuntimeException("Unable to find patients." + e.getMessage(), e);
        }
    }

    @Override
    public void update(Patient patient) {
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE patient SET patient_no = ?,first_name = ?,sur_name = ?,address = ?,telephone_number = ?,dob = ? WHERE id = ?")) {
            stmt.setString(1, patient.getPatientNo());
            stmt.setString(2, patient.getFirstName());
            stmt.setString(3, patient.getSurName());
            stmt.setString(4, patient.getAddress());
            stmt.setString(5, patient.getTelephoneNumber());
            stmt.setDate(6, Date.valueOf(patient.getDob()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to update patient. SQLException");
            throw new RuntimeException("Unable to update patient." + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM patient WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to delete patient. SQLException");
            throw new RuntimeException("Unable to delete patient." + e.getMessage(), e);
        }
    }
}
