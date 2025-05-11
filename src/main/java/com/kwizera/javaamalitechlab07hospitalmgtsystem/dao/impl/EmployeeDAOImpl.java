package com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.impl;

import com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.EmployeeDAO;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.RoleDAO;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.Employee;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.Role;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.utils.CustomLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    private Connection connection;
    private RoleDAO roleDAO;

    @Override
    public Employee findById(int id) {
        Employee employee = null;

        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM employee WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                employee = new Employee();

                int roleId = rs.getInt("role_id");
                Role role = roleDAO.findById(roleId);

                employee.setId(rs.getInt("id"));
                employee.setFirstName(rs.getString("first_name"));
                employee.setSurName(rs.getString("sur_name"));
                employee.setAddress(rs.getString("address"));
                employee.setEmployeeNo(rs.getString("employee_no"));
                employee.setTelephoneNumber(rs.getString("telephone_number"));
                employee.setSalary(rs.getDouble("salary"));
                employee.setRole(role);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to find employee by id. SQLException");
        }
        return employee;
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM employee");
            while (rs.next()) {
                Employee employee = new Employee();

                int roleId = rs.getInt("role_id");
                Role role = roleDAO.findById(roleId);

                employee.setId(rs.getInt("id"));
                employee.setFirstName(rs.getString("first_name"));
                employee.setSurName(rs.getString("sur_name"));
                employee.setAddress(rs.getString("address"));
                employee.setEmployeeNo(rs.getString("employee_no"));
                employee.setTelephoneNumber(rs.getString("telephone_number"));
                employee.setSalary(rs.getDouble("salary"));
                employee.setRole(role);

                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to find employees. SQLException");
        }
        return employees;
    }

    @Override
    public void save(Employee employee) {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO employee (employee_no,first_name,sur_name,address,telephone_number,salary,role_id) VALUES (?,?,?,?,?,?)")) {
            stmt.setString(1, employee.getEmployeeNo());
            stmt.setString(2, employee.getFirstName());
            stmt.setString(3, employee.getSurName());
            stmt.setString(4, employee.getSurName());
            stmt.setString(5, employee.getAddress());
            stmt.setString(6, employee.getTelephoneNumber());
            stmt.setString(6, employee.getTelephoneNumber());
            stmt.setDouble(7, employee.getSalary());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to save employee. SQLException");
        }
    }

    @Override
    public void update(Employee employee) {
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE employee SET name = ?, employee_no = ?, first_name = ?, sur_name = ?, address = ?, telephone_number = ?, salary = ?, role_id = ? WHERE id = ?")) {
            stmt.setString(1, employee.getEmployeeNo());
            stmt.setString(2, employee.getFirstName());
            stmt.setString(3, employee.getSurName());
            stmt.setString(4, employee.getSurName());
            stmt.setString(5, employee.getAddress());
            stmt.setString(6, employee.getTelephoneNumber());
            stmt.setDouble(7, employee.getSalary());
            stmt.setInt(8, employee.getRole().getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to update employee. SQLException");
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM employee WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to delete employee. SQLException");
        }
    }
}
