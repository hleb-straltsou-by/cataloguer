package com.gv.cataloguer.database.settings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {
    private static final String DATABASE_URL = "database.url";
    private static final String DATABASE_NAME = "database.name";
    private static final String DATABASE_USER = "database.user";
    private static final String DATABASE_PASSWORD = "database.password";

    private DatabaseConnectionManager(){}

    public static Connection getDatabaseConnection(){
        String dbUrl = DatabaseConfigurationManager.getProperty(DATABASE_URL);
        String dbName = DatabaseConfigurationManager.getProperty(DATABASE_NAME);
        String dbUser = DatabaseConfigurationManager.getProperty(DATABASE_USER);
        String dbPassword = DatabaseConfigurationManager.getProperty(DATABASE_PASSWORD);
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(dbUrl + dbName, dbUser, dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return connection;
        }
    }
}
