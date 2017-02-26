package com.gv.cataloguer.authenthication.dao;

import com.gv.cataloguer.database.settings.DatabaseConnectionManager;
import com.gv.cataloguer.models.Role;
import com.gv.cataloguer.models.User;
import java.sql.*;

public class UserDaoSingleton implements UserDao {

    private static final UserDaoSingleton INSTANCE = new UserDaoSingleton();
    private static final int USER_LOGIN_INDEX = 1;
    private static final int USER_PASSWORD_INDEX = 2;
    private static final int USER_ID_INDEX = 3;
    private static final int USER_ROLE_INDEX = 4;
    private static final int USER_NAME_INDEX = 5;

    private UserDaoSingleton() {
    }

    public static UserDaoSingleton getInstance() {
        return INSTANCE;
    }

    public User getUser(String login, String password) throws SQLException, ClassNotFoundException {
        User user = null;
        Connection connection = DatabaseConnectionManager.getDatabaseConnection();
        CallableStatement stmt = connection.prepareCall("call get_user(?, ?, ?, ?, ?)");
        stmt.setString(USER_LOGIN_INDEX, login);
        stmt.setString(USER_PASSWORD_INDEX, password);
        stmt.registerOutParameter(USER_ID_INDEX, Types.INTEGER);
        stmt.registerOutParameter(USER_ROLE_INDEX, Types.VARCHAR);
        stmt.registerOutParameter(USER_NAME_INDEX, Types.VARCHAR);
        stmt.execute();
        if(stmt.getInt(USER_ID_INDEX) != 0) {
            user = new User(stmt.getInt(USER_ID_INDEX), login,
                    stmt.getString(USER_NAME_INDEX), Role.valueOf(stmt.getString(USER_ROLE_INDEX)));
        }
        return user;
    }
}
