package com.gv.cataloguer.authenthication.validation;

import com.gv.cataloguer.models.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserValidatorTest {

    private ApplicationContext context;

    private UserValidator validator;

    private final static String VALIDATOR_BEAN = "userValidator";

    @Before
    public void initialize(){
        context = new ClassPathXmlApplicationContext("IoC/authenthication-context.xml");
        validator = (UserValidator) context.getBean(VALIDATOR_BEAN);
    }

    @Test
    public void checkNonExistLogin() throws Exception {
        String login = "test@gmail.com";
        String password = "1234";
        User user = validator.checkLogin(login, password);
        Assert.assertNull(user);
    }

}