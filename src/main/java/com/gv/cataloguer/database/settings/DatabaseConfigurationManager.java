package com.gv.cataloguer.database.settings;

import java.util.ResourceBundle;

public class DatabaseConfigurationManager {
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("databaseConfig");

    private DatabaseConfigurationManager(){}

    public static String getProperty(String key){
        return resourceBundle.getString(key);
    }
}
