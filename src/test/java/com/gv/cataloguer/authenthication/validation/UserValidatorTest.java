package com.gv.cataloguer.authenthication.validation;

import com.gv.cataloguer.authenthication.dao.UserDaoJDBC;
import com.gv.cataloguer.models.User;
import org.junit.Assert;
import org.junit.Test;

public class UserValidatorTest {

    UserValidator userValidator = new UserValidator(new UserDaoJDBC());

    @Test
    public void checkLogin() throws Exception {
        String login = "gleb.streltsov@gmail.com";
        String password = "44447777";
        User user = userValidator.checkLogin(login, password);
        Assert.assertNotNull(user);
        Assert.assertEquals(1, user.getUserId());

        login = "test";
        password = "test";
        user = userValidator.checkLogin(login, password);
        Assert.assertNull(user);
        login = null;
        password = null;
        user = userValidator.checkLogin(login, password);
        Assert.assertNull(user);
    }

}