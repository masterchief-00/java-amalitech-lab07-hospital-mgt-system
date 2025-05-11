package com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.impl;

import com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.HospitalizationDAO;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.PatientDAO;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.Hospitalization;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.Patient;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.utils.CustomLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HospitalizationDAOImpl implements HospitalizationDAO {
    private Connection connection;
    private PatientDAO patientDAO;

    @Override
    public Hospitalization findById(int id) {
        Hospitalization hospitalization = null;
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM hospitalization WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                hospitalization = new Hospitalization();
                Patient patient = patientDAO.findById(rs.getInt("patient_id"));
                hospitalization.setId(rs.getInt("id"));
                hospitalization.setPatient(patient);
                hospitalization.setStartDate(rs.getDate("start_date").toLocalDate());
                hospitalization.setEndDate(rs.getDate("end_date").toLocalDate());
                hospitalization.setNotes(rs.getString("notes"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to find hospitalization by id. SQLException");
        }

        return hospitalization;
    }

    @Override
    public List<Hospitalization> findAll() {
        List<Hospitalization> hospitalizations = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM hospitalization");
            while (rs.next()) {
                Hospitalization hospitalization = new Hospitalization();
                Patient patient = patientDAO.findById(rs.getInt("patient_id"));
                hospitalization.setId(rs.getInt("id"));
                hospitalization.setPatient(patient);
                hospitalization.setStartDate(rs.getDate("start_date").toLocalDate());
                hospitalization.setEndDate(rs.getDate("end_date").toLocalDate());
                hospitalization.setNotes(rs.getString("notes"));
                hospitalizations.add(hospitalization);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to find hospitalizations. SQLException");
        }
        return hospitalizations;
    }

    @Override
    public void save(Hospitalization hospitalization) {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO hospitalization (patient_id,start_date,end_date,notes) VALUES (?,?,?,?)")) {
            stmt.setInt(1, hospitalization.getPatient().getId());
            stmt.setDate(2, Date.valueOf(hospitalization.getStartDate()));
            stmt.setDate(3, Date.valueOf(hospitalization.getEndDate()));
            stmt.setString(4, hospitalization.getNotes());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to save hospitalization. SQLException");
        }
    }

    @Override
    public void update(Hospitalization hospitalization) {
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE patient SET patient_id = ?,start_date = ?,end_date = ?,notes = ? WHERE id = ?")) {
            stmt.setInt(1, hospitalization.getPatient().getId());
            stmt.setDate(2, Date.valueOf(hospitalization.getStartDate()));
            stmt.setDate(3, Date.valueOf(hospitalization.getEndDate()));
            stmt.setString(4, hospitalization.getNotes());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to update hospitalization. SQLException");
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM hospitalization WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to delete hospitalization. SQLException");
        }
    }
}
