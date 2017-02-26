package com.gv.cataloguer.authenthication.dao;

import com.gv.cataloguer.database.settings.DatabaseConnectionManager;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserDaoSingletonTest {
    @Test
    public void getUser() throws Exception {
        Assert.assertEquals(1, UserDaoSingleton.getInstance().
                getUser("gleb.streltsov@gmail.com", "44447777").getUserId());
        Assert.assertEquals(2, UserDaoSingleton.getInstance().
                getUser("vi@gmail.com", "4477").getUserId());
        Assert.assertNull(UserDaoSingleton.getInstance().getUser("test@gmail.com", "test"));

    }

}