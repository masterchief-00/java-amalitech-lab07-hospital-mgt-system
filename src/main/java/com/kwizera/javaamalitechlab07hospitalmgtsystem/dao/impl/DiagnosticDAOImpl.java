package com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.impl;

import com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.DiagnosticDAO;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.DoctorDAO;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.HospitalizationDAO;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.*;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.utils.CustomLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiagnosticDAOImpl implements DiagnosticDAO {
    private final Connection connection;
    private HospitalizationDAO hospitalizationDAO;
    private DoctorDAO doctorDAO;

    public DiagnosticDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Diagnostic findById(int id) {
        Diagnostic diagnostic = null;
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM diagnostic WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                diagnostic = new Diagnostic();
                Hospitalization hospitalization = hospitalizationDAO.findById(rs.getInt("hospitalization_id"));
                Doctor doctor = doctorDAO.findById(rs.getInt("doctor_id"));
                diagnostic.setId(rs.getInt("id"));
                diagnostic.setHospitalization(hospitalization);
                diagnostic.setDoctor(doctor);
                diagnostic.setDiagnosis(rs.getString("diagnosis"));
                diagnostic.setTreatment(rs.getString("treatment"));
                diagnostic.setDate(rs.getDate("date").toLocalDate());

            }
        } catch (SQLException e) {
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to find diagnostic by id. SQLException");
            throw new RuntimeException("Unable to find diagnostic by id. " + e.getMessage(), e);
        }

        return diagnostic;
    }

    @Override
    public List<Diagnostic> findAll() {
        List<Diagnostic> diagnostics = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM diagnostic");
            while (rs.next()) {
                Diagnostic diagnostic = new Diagnostic();
                Hospitalization hospitalization = hospitalizationDAO.findById(rs.getInt("hospitalization_id"));
                Doctor doctor = doctorDAO.findById(rs.getInt("doctor_id"));
                diagnostic.setId(rs.getInt("id"));
                diagnostic.setHospitalization(hospitalization);
                diagnostic.setDoctor(doctor);
                diagnostic.setDiagnosis(rs.getString("diagnosis"));
                diagnostic.setTreatment(rs.getString("treatment"));
                diagnostic.setDate(rs.getDate("date").toLocalDate());
                diagnostics.add(diagnostic);
            }
        } catch (SQLException e) {
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to find diagnostics. SQLException");
            throw new RuntimeException("Unable to find diagnostics. " + e.getMessage(), e);
        }
        return diagnostics;
    }

    @Override
    public void save(Diagnostic diagnostic) {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO diagnostic (hospitalization_id,doctor_id,diagnosis,treatment,date) VALUES (?,?,?,?,?)")) {
            stmt.setInt(1, diagnostic.getHospitalization().getId());
            stmt.setInt(2, diagnostic.getDoctor().getId());
            stmt.setString(3, diagnostic.getDiagnosis());
            stmt.setString(4, diagnostic.getTreatment());
            stmt.setDate(5, Date.valueOf(diagnostic.getDate()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to save diagnostic. SQLException");
            throw new RuntimeException("Unable to save diagnostic. " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Diagnostic diagnostic) {
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE diagnostic SET hospitalization_id = ?,doctor_id = ?,diagnosis = ?,treatment = ?,date = ? WHERE id = ?")) {
            stmt.setInt(1, diagnostic.getHospitalization().getId());
            stmt.setInt(2, diagnostic.getDoctor().getId());
            stmt.setString(3, diagnostic.getDiagnosis());
            stmt.setString(4, diagnostic.getTreatment());
            stmt.setDate(5, Date.valueOf(diagnostic.getDate()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to update diagnostic. SQLException");
            throw new RuntimeException("Unable to update diagnostic. " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM diagnostic WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to delete diagnostic. SQLException");
            throw new RuntimeException("Unable to delete diagnostic. " + e.getMessage(), e);
        }
    }
}
