package com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.impl;

import com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.AdmissionDAO;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.BedDAO;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.HospitalizationDAO;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.WardDAO;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.*;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.utils.CustomLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdmissionDAOImpl implements AdmissionDAO {
    private final Connection connection;
    private HospitalizationDAO hospitalizationDAO;
    private BedDAO bedDAO;
    private WardDAO wardDAO;

    public AdmissionDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Admission findById(int id) {
        Admission admission = null;
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM admission WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                admission = new Admission();
                Hospitalization hospitalization = hospitalizationDAO.findById(rs.getInt("hospitalization_id"));
                Bed bed = bedDAO.findById(rs.getInt("bed_id"));
                Ward ward = wardDAO.findById(rs.getInt("ward_id"));
                admission.setId(rs.getInt("id"));
                admission.setBed(bed);
                admission.setWard(ward);
                admission.setHospitalization(hospitalization);
                admission.setAdmittedAt(rs.getDate("admitted_at").toLocalDate());
                admission.setDischargedAt(rs.getDate("discharged_at").toLocalDate());
                admission.setNotes(rs.getString("notes"));

            }
        } catch (SQLException e) {
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to find admission by id. SQLException");
            throw new RuntimeException("Unable to find admission by id." + e.getMessage(), e);
        }

        return admission;
    }

    @Override
    public List<Admission> findAll() {
        List<Admission> admissions = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM admission");
            while (rs.next()) {
                Admission admission = new Admission();
                Hospitalization hospitalization = hospitalizationDAO.findById(rs.getInt("hospitalization_id"));
                Bed bed = bedDAO.findById(rs.getInt("bed_id"));
                Ward ward = wardDAO.findById(rs.getInt("ward_id"));
                admission.setId(rs.getInt("id"));
                admission.setBed(bed);
                admission.setWard(ward);
                admission.setHospitalization(hospitalization);
                admission.setAdmittedAt(rs.getDate("admitted_at").toLocalDate());
                admission.setDischargedAt(rs.getDate("discharged_at").toLocalDate());
                admission.setNotes(rs.getString("notes"));

                admissions.add(admission);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to find admissions. SQLException");
            throw new RuntimeException("Unable to find admissions. " + e.getMessage(), e);
        }
        return admissions;
    }

    @Override
    public void save(Admission admission) {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO admission (hospitalization_id,ward_id,bed_id,admitted_at,discharged_at,notes) VALUES (?,?,?,?,?)")) {
            stmt.setInt(1, admission.getHospitalization().getId());
            stmt.setInt(2, admission.getWard().getId());
            stmt.setInt(3, admission.getBed().getId());
            stmt.setDate(4, Date.valueOf(admission.getAdmittedAt()));
            stmt.setDate(5, Date.valueOf(admission.getDischargedAt()));
            stmt.setString(6, admission.getNotes());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to save admission. SQLException");
            throw new RuntimeException("Unable to save admission. " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Admission admission) {
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE admission SET hospitalization_id = ?,ward_id = ?,bed_id = ?,admitted_at = ?,discharged_at = ?,notes = ? WHERE id = ?")) {
            stmt.setInt(1, admission.getHospitalization().getId());
            stmt.setInt(2, admission.getWard().getId());
            stmt.setInt(3, admission.getBed().getId());
            stmt.setDate(4, Date.valueOf(admission.getAdmittedAt()));
            stmt.setDate(5, Date.valueOf(admission.getDischargedAt()));
            stmt.setString(6, admission.getNotes());
            stmt.executeUpdate();
        } catch (SQLException e) {
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to update admission. SQLException");
            throw new RuntimeException("Unable to update admission. " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM admission WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to delete admission. SQLException");
            throw new RuntimeException("Unable to delete admission. " + e.getMessage(), e);
        }
    }
}
