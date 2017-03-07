package com.gv.cataloguer.database.settings;

import java.util.ResourceBundle;

/**
 * Not-instantiated class that gets database configuration properties
 * from resource bundle databaseConfig.properties
 */
public class DatabaseConfigurationManager {

    /** object for extracting properties from resource bundle cryptoKey.properties */
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("databaseConfig");

    /**
     * private constructor of class, which implement non-instantiation
     */
    private DatabaseConfigurationManager(){}

    /**
     * gets string property from resource bundle
     * @param key - key of extracting property
     * @return string value of property
     */
    public static String getProperty(String key){
        return resourceBundle.getString(key);
    }
}
