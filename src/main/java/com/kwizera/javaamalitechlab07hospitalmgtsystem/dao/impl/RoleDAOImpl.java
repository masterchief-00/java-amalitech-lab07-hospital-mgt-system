package com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.impl;

import com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.RoleDAO;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.Role;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.utils.CustomLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDAOImpl implements RoleDAO {

    private final Connection connection;

    public RoleDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Role findById(int id) {

        Role role = null;
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM role WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                role = new Role();
                role.setId(rs.getInt("id"));
                role.setName(rs.getString("name"));
            }
        } catch (SQLException e) {
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to find role by id. SQLException");
            throw new RuntimeException("Unable to find role by id." + e.getMessage(), e);
        }

        return role;
    }

    @Override
    public List<Role> findAll() {
        List<Role> roles = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM role");
            while (rs.next()) {
                Role role = new Role();
                role.setId(rs.getInt("id"));
                role.setName(rs.getString("name"));
                roles.add(role);
            }
        } catch (SQLException e) {
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to find roles. SQLException");
            throw new RuntimeException("Unable to find roles." + e.getMessage(), e);
        }
        return roles;
    }

    @Override
    public void save(Role role) {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO role (name) VALUES (?)")) {
            stmt.setString(1, role.getName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to save role. SQLException");
            throw new RuntimeException("Unable to save role." + e.getMessage(), e);
        }
    }

    @Override
    public void update(Role role) {
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE role SET name = ? WHERE id = ?")) {
            stmt.setString(1, role.getName());
            stmt.setInt(2, role.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to update role. SQLException");
            throw new RuntimeException("Unable to update role. SQLException" + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM role WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to delete role. SQLException");
            throw new RuntimeException("Unable to delete role. SQLException" + e.getMessage(), e);
        }
    }
}
