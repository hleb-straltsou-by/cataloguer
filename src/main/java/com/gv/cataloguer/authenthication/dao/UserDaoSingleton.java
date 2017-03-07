package com.gv.cataloguer.authenthication.dao;

import com.gv.cataloguer.database.settings.DatabaseConnectionManager;
import com.gv.cataloguer.models.Role;
import com.gv.cataloguer.models.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDaoSingleton implements UserDao {

    private static final UserDaoSingleton INSTANCE = new UserDaoSingleton();
    private static final int USER_LOGIN_INDEX = 1;
    private static final int USER_PASSWORD_INDEX = 2;
    private static final int USER_ID_INDEX = 3;
    private static final int USER_ROLE_INDEX = 4;
    private static final int USER_NAME_INDEX = 5;

    private UserDaoSingleton() {}

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

    @Override
    public List<String> getAllUserEmails() {
        List<String> emails = null;
        try {
            Connection connection = DatabaseConnectionManager.getDatabaseConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT email from emails");
            ResultSet rS = stmt.executeQuery();
            rS.last();
            int rowsCount = rS.getRow();
            emails = new ArrayList<>(rowsCount);
            rS.beforeFirst();
            while(rS.next()){
                emails.add(rS.getString("email"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            return emails;
        }
    }

    @Override
    public Object[] getLastUpdateAndTraffic(int userId) {
        Object[] lastUpdateAndTraffic = new Object[2];
        try {
            Connection connection = DatabaseConnectionManager.getDatabaseConnection();
            PreparedStatement stmt = connection
                    .prepareStatement("SELECT last_update, traffic from users_activity where id_user = ?");
            stmt.setInt(1, userId);
            ResultSet rS = stmt.executeQuery();
            rS.next();
            lastUpdateAndTraffic[0] = rS.getDate("last_update");
            lastUpdateAndTraffic[1] = rS.getInt("traffic");
        } catch (SQLException e){
            System.out.println(e);
        } finally {
            return lastUpdateAndTraffic;
        }
    }

    @Override
    public void setLastUpdateAndTraffic(int userId, Date newLastUpdate, int newTraffic) {
        try {
            Connection connection = DatabaseConnectionManager.getDatabaseConnection();
            PreparedStatement stmt = connection
                    .prepareStatement("UPDATE users_activity SET last_update = ?, traffic = ?");
            stmt.setDate(1, new java.sql.Date(newLastUpdate.getTime()));
            stmt.setInt(2, newTraffic);
            stmt.executeUpdate();
        } catch (SQLException e){
            System.out.println(e);
        }
    }
}
