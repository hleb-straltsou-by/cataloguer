package com.gv.cataloguer.authenthication.validation;

import com.gv.cataloguer.authenthication.dao.UserDao;
import com.gv.cataloguer.models.User;

/**
 * Not-instantiated class for checking users login and password,
 * delegates getting of User object to UserDao pattern
 */
public class UserValidator {

    private UserDao userDao;

    public UserValidator(UserDao userDao){
        this.userDao = userDao;
    }

    /**
     * delegates getting of User object @see User to UserDao pattern,
     * if appeared exceptions, then used AppLogger object @see AppLogger for logging issues
     * @param login - users login property
     * @param password - users encrypt password property, stored only in data storage
     * @return User object
     */
    public User checkLogin(String login, String password) {
        return userDao.getUser(login, password);
    }
}
