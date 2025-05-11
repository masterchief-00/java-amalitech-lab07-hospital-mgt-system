package com.kwizera.javaamalitechlab07hospitalmgtsystem.Session;

import com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.PatientDAO;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.dao.impl.PatientDAOImpl;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.services.PatientService;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.services.impl.PatientServiceImpl;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.utils.CustomLogger;
import com.kwizera.javaamalitechlab07hospitalmgtsystem.utils.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class SessionManager {
    private static SessionManager instance = new SessionManager();
    private Connection connection;
    private final PatientService patientService;
    private final PatientDAO patientDAO;

    private SessionManager() {
        try {
            this.connection = DBConnection.getConnection();
        } catch (SQLException e) {
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to establish database connection. SQLException");
        }
        this.patientDAO = new PatientDAOImpl(connection);
        this.patientService = new PatientServiceImpl(patientDAO);
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public PatientService getPatientService() {
        return patientService;
    }

    public Connection getConnection() {
        if (connection != null) {
            return connection;
        } else {
            CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to find database connection");
            throw new RuntimeException("No Database connection found");
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                instance = null;
            } catch (SQLException e) {
                CustomLogger.log(CustomLogger.LogLevel.ERROR, "Unable to close database connection. SQLException");
                throw new RuntimeException("Unable to close database connection.");
            }
        }
    }
}
