package com.gv.cataloguer.controllers;

import com.gv.cataloguer.authenthication.validation.UserValidator;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class FormControllerTest {

    @Test
    public void logIn() throws Exception {
        Assert.assertEquals(1, UserValidator.checkLogin("gleb.streltsov@gmail.com",
                "44447777").getUserId());
        Assert.assertEquals(2, UserValidator.checkLogin("vi@gmail.com",
                "4477").getUserId());
        Assert.assertNull(UserValidator.checkLogin("test@gmail.com", "test"));

    }

}