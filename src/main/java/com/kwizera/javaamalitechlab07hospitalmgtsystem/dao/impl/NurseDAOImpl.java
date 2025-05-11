package com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.impl;

import com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.EmployeeDAO;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.NurseDAO;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.Employee;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.Nurse;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.utils.CustomLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NurseDAOImpl implements NurseDAO {
    private Connection connection;
    private EmployeeDAO employeeDAO;

    @Override
    public Nurse findById(int id) {
        Nurse nurse = null;
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM nurse WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nurse = new Nurse();
                Employee employee = employeeDAO.findById(rs.getInt("employee_id"));
                nurse.setId(rs.getInt("id"));
                nurse.setRotation(rs.getString("rotation"));
                nurse.setEmployee(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to find nurse by id. SQLException");
        }

        return nurse;
    }

    @Override
    public List<Nurse> findAll() {
        List<Nurse> nurses = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM nurse");
            while (rs.next()) {
                Nurse nurse = new Nurse();
                Employee employee = employeeDAO.findById(rs.getInt("employee_id"));

                nurse.setId(rs.getInt("id"));
                nurse.setRotation(rs.getString("rotation"));
                nurse.setEmployee(employee);
                nurses.add(nurse);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to find nurses. SQLException");
        }
        return nurses;
    }

    @Override
    public void save(Nurse nurse) {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO nurse (rotation,employee_id) VALUES (?,?)")) {
            stmt.setString(1, nurse.getRotation());
            stmt.setInt(2, nurse.getEmployee().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to save nurse. SQLException");
        }
    }

    @Override
    public void update(Nurse nurse) {
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE nurse SET rotation = ?, employee_id = ? WHERE id = ?")) {
            stmt.setString(1, nurse.getRotation());
            stmt.setInt(2, nurse.getEmployee().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to update nurse. SQLException");
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM nurse WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to delete nurse. SQLException");
        }
    }
}
