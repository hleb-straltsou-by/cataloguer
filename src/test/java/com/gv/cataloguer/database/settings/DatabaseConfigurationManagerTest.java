package com.gv.cataloguer.database.settings;

import org.junit.Assert;
import org.junit.Test;

public class DatabaseConfigurationManagerTest {

    private static final String DATABASE_URL = "database.url";
    private static final String DATABASE_NAME = "database.name";
    private static final String DATABASE_USER = "database.user";
    private static final String DATABASE_PASSWORD = "database.password";

    @Test
    public void getProperty() throws Exception {
        Assert.assertEquals("jdbc:mysql://localhost:3306/",
                DatabaseConfigurationManager.getProperty(DATABASE_URL));
        Assert.assertEquals("cataloguer",
                DatabaseConfigurationManager.getProperty(DATABASE_NAME));
        Assert.assertEquals("root",
                DatabaseConfigurationManager.getProperty(DATABASE_USER));
        Assert.assertEquals("",
                DatabaseConfigurationManager.getProperty(DATABASE_PASSWORD));
    }

}