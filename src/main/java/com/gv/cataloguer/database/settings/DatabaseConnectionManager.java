package com.gv.cataloguer.database.settings;

import com.gv.cataloguer.logging.AppLogger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Not-instantiated class for getting database connection
 */
public class DatabaseConnectionManager {

    /** key for value of database url */
    private static final String DATABASE_URL = "database.url";

    /** key for value of database name */
    private static final String DATABASE_NAME = "database.name";

    /** key for value of database user */
    private static final String DATABASE_USER = "database.user";

    /** key for value of database password */
    private static final String DATABASE_PASSWORD = "database.password";

    /**
     * private constructor of class, which implement non-instantiation
     */
    private DatabaseConnectionManager(){}

    /**
     * gets returns connection to specified database connection
     * @return connection to specified database
     */
    public static Connection getDatabaseConnection(){
        String dbUrl = DatabaseConfigurationManager.getProperty(DATABASE_URL);
        String dbName = DatabaseConfigurationManager.getProperty(DATABASE_NAME);
        String dbUser = DatabaseConfigurationManager.getProperty(DATABASE_USER);
        String dbPassword = DatabaseConfigurationManager.getProperty(DATABASE_PASSWORD);
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(dbUrl + dbName +
                    "?autoReconnect=true&useSSL=false", dbUser, dbPassword);
        } catch (SQLException e) {
            AppLogger.getLogger().error(e.getMessage());
        } finally {
            return connection;
        }
    }
}
