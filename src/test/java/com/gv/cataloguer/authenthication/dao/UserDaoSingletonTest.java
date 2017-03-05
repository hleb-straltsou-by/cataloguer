package com.gv.cataloguer.authenthication.dao;

import org.junit.Assert;
import org.junit.Test;

public class UserDaoSingletonTest {

    @Test
    public void getUser() throws Exception {
        Assert.assertEquals(1, UserDaoSingleton.getInstance().
                getUser("gleb.streltsov@gmail.com", "44447777").getUserId());
        Assert.assertEquals(2, UserDaoSingleton.getInstance().
                getUser("vi@gmail.com", "4477").getUserId());
        Assert.assertNull(UserDaoSingleton.getInstance().getUser("test@gmail.com", "test"));
    }

    @Test
    public void getLastModifiedAndTraffic() throws Exception {
        Assert.assertNull(UserDaoSingleton.getInstance().getLastUpdateAndTraffic(1)[0]);
        Assert.assertEquals(0, UserDaoSingleton.getInstance().getLastUpdateAndTraffic(1)[1]);
    }

}