package com.gv.cataloguer.database.settings;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class DatabaseConnectionManagerTest {
    @Test
    public void getDatabaseConnection() throws Exception {
        Assert.assertNotNull(DatabaseConnectionManager.getDatabaseConnection());
    }

}