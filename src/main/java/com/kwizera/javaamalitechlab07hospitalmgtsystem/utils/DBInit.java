package com.kwizera.javaamalitechlab07hospitalmgtsystem.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInit {
    public static void runSchemaSetup(Connection connection) {
        try {
            String sql = Files.readString(Paths.get("src/main/resources/com/kwizera/javaamalitechlab07hospitalmgtsystem/db/schema.sql"));
            Statement statement = connection.createStatement();
            statement.execute(sql);
            CustomLogger.log(CustomLogger.LogLevel.INFO, "Database connection established, schema file executed");
        } catch (IOException | SQLException e) {
            if (e instanceof IOException) {
                CustomLogger.log(CustomLogger.LogLevel.ERROR, "Connection to the database failed");
            } else {
                CustomLogger.log(CustomLogger.LogLevel.ERROR, "Schema file execution failed");
            }

        }
    }
}
