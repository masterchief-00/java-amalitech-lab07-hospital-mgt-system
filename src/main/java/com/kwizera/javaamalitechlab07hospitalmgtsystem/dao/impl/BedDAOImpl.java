package com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.impl;

import com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.BedDAO;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.WardDAO;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.*;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.utils.CustomLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BedDAOImpl implements BedDAO {
    private Connection connection;
    private WardDAO wardDAO;

    @Override
    public Bed findById(int id) {
        Bed bed = null;
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM bed WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                bed = new Bed();
                Ward ward = wardDAO.findById(rs.getInt("ward_id"));
                bed.setId(rs.getInt("id"));
                bed.setBedNo(rs.getString("bed_no"));
                bed.setWard(ward);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to find bed by id. SQLException");
        }

        return bed;
    }

    @Override
    public List<Bed> findAll() {
        List<Bed> beds = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM bed");
            while (rs.next()) {
                Bed bed = new Bed();
                Ward ward = wardDAO.findById(rs.getInt("ward_id"));
                bed.setId(rs.getInt("id"));
                bed.setBedNo(rs.getString("bed_no"));
                bed.setWard(ward);
                beds.add(bed);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to find beds. SQLException");
        }
        return beds;
    }

    @Override
    public void save(Bed bed) {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO bed (ward_id,bed_no) VALUES (?,?)")) {
            stmt.setInt(1, bed.getWard().getId());
            stmt.setString(2, bed.getBedNo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to save bed. SQLException");
        }
    }

    @Override
    public void update(Bed bed) {
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE bed SET ward_id = ?, bed_no = ? WHERE id = ?")) {
            stmt.setInt(1, bed.getWard().getId());
            stmt.setString(2, bed.getBedNo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to update bed. SQLException");
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM bed WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to delete bed. SQLException");
        }
    }
}
