package com.gv.cataloguer.authenthication.validation;

import com.gv.cataloguer.authenthication.dao.UserDaoSingleton;
import com.gv.cataloguer.models.User;
import java.sql.SQLException;

public class UserValidator {

    public static User checkLogin(String login, String password) {
        User user = null;
        try {
            user = UserDaoSingleton.getInstance().getUser(login, password);
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        } finally {
            return user;
        }
    }
}
