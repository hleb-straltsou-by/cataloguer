package com.gv.cataloguer.authenthication.validation;

import com.gv.cataloguer.authenthication.dao.UserDaoSingleton;
import com.gv.cataloguer.logging.AppLogger;
import com.gv.cataloguer.models.User;
import java.sql.SQLException;

/**
 * Not-instantiated class for checking users login and password,
 * delegates getting of User object to UserDao pattern
 */
public class UserValidator {

    /**
     * private constructor of class, which implement non-instantiation
     */
    private UserValidator(){}

    /**
     * delegates getting of User object @see User to UserDao pattern,
     * if appeared exceptions, then used AppLogger object @see AppLogger for logging issues
     * @param login - users login property
     * @param password - users encrypt password property, stored only in data storage
     * @return User object
     */
    public static User checkLogin(String login, String password) {
        User user = null;
        try {
            user = UserDaoSingleton.getInstance().getUser(login, password);
        } catch (SQLException | ClassNotFoundException e){
            AppLogger.getLogger().error(e.getMessage());
        } finally {
            return user;
        }
    }
}
