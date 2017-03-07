package com.gv.cataloguer.authenthication.validation;

import com.gv.cataloguer.models.User;
import org.junit.Assert;
import org.junit.Test;

public class UserValidatorTest {
    @Test
    public void checkLogin() throws Exception {
        String login = "gleb.streltsov@gmail.com";
        String password = "44447777";
        User user = UserValidator.checkLogin(login, password);
        Assert.assertNotNull(user);
        Assert.assertEquals(1, user.getUserId());

        login = "test";
        password = "test";
        user = UserValidator.checkLogin(login, password);
        Assert.assertNull(user);
        login = null;
        password = null;
        user = UserValidator.checkLogin(login, password);
        Assert.assertNull(user);
    }

}