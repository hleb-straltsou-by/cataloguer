package com.gv.cataloguer.authenthication.dao;

import com.gv.cataloguer.cryptography.CryptographerXOR;
import com.gv.cataloguer.database.settings.DatabaseConnectionManager;
import com.gv.cataloguer.logging.AppLogger;
import com.gv.cataloguer.models.Role;
import com.gv.cataloguer.models.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDaoSingleton implements UserDao {

    /** single instance of class */
    private static final UserDaoSingleton INSTANCE = new UserDaoSingleton();

    /** index of parameter in stored procedure with users login */
    private static final int USER_LOGIN_INDEX = 1;

    /** index of parameter stored procedure with users password */
    private static final int USER_PASSWORD_INDEX = 2;

    /** index of parameter stored procedure with users id */
    private static final int USER_ID_INDEX = 3;

    /** index of parameter stored procedure with users role */
    private static final int USER_ROLE_INDEX = 4;

    /** index of parameter stored procedure with users name */
    private static final int USER_NAME_INDEX = 5;

    /**
     * private constructor of class for implementing singleton pattern
     */
    private UserDaoSingleton() {}

    /**
     * Returns single instance of class
     * @return UserDaoSingleton instance
     */
    public static UserDaoSingleton getInstance() {
        return INSTANCE;
    }

    /**
     * searches and returns User object @see User according password and login that stored in remote database
     * @param login - users login property
     * @param password - users password property, stored only in remote database
     * @return User object
     * @throws SQLException - if there are troubles in SQL logic
     * @throws ClassNotFoundException - if database driver is not loaded
     */
    public User getUser(final String login, final String password) throws SQLException, ClassNotFoundException {
        User user = null;
        Connection connection = DatabaseConnectionManager.getDatabaseConnection();
        CallableStatement stmt = connection.prepareCall("call get_user(?, ?, ?, ?, ?)");
        stmt.setString(USER_LOGIN_INDEX, login);
        stmt.setString(USER_PASSWORD_INDEX, CryptographerXOR.getInstance().decrypt(password));
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
    /**
     * retrieves all email addresses of registered users from remote database
     * @return list of users emails in String objects
     */
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
            AppLogger.getLogger().error(e.getMessage());
        } finally {
            return emails;
        }
    }

    @Override
    /**
     * searches and returns two-sized array of Objects of default users values of last added files and its size
     * from remote database
     * @param userId - primary key of registered user
     * @return two-sized array of Objects, first element of array - java.sql.Date object,
     * second - added traffic in megabytes
     */
    public Object[] getLastUpdateAndTraffic(final int userId) {
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
            AppLogger.getLogger().error(e.getMessage());
        } finally {
            return lastUpdateAndTraffic;
        }
    }

    @Override
    /**
     * Sets new lastUpdate date and used traffic in megabytes in remote database
     * @param userId - primary key of registered user
     * @param newLastUpdate - date of last added files
     * @param newTraffic - used traffic in megabytes
     */
    public void setLastUpdateAndTraffic(final int userId, final Date newLastUpdate, final int newTraffic) {
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
