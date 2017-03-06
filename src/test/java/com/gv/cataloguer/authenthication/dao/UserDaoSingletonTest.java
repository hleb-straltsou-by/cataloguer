package com.gv.cataloguer.authenthication.dao;

import org.junit.Assert;
import org.junit.Test;
import java.util.List;

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
    public void getAllUserEmails() throws Exception {
        List<String> emails = UserDaoSingleton.getInstance().getAllUserEmails();
        String email1 = "gleb.streltsov.4by@gmail.com";
        String email2 = "vi@gmail.com";
        Assert.assertTrue(emails.contains(email1));
        Assert.assertTrue(emails.contains(email2));
    }
}