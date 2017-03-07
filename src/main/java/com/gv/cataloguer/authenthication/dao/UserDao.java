package com.gv.cataloguer.authenthication.dao;

import com.gv.cataloguer.models.User;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * This interface provides methods for accessing data storage with
 * information about users @see User
 */
public interface UserDao {

    /**
     * searches and returns User object @see User according password and login that stored in data storage
     * @param login - users login property
     * @param password - users password property, stored only in data storage
     * @return User object
     * @throws SQLException - if there are troubles in SQL logic
     * @throws ClassNotFoundException - if database driver is not loaded
     */
    User getUser(final String login, final String password) throws SQLException, ClassNotFoundException;

    /**
     * retrieves all email addresses of registered users from data storage
     * @return list of users emails in String objects
     */
    List<String> getAllUserEmails();

    /**
     * searches and returns two-sized array of Objects of default users values of last added files and its size
     * from data storage
     * @param userId - primary key of registered user
     * @return two-sized array of Objects, first element of array - java.sql.Date object,
     * second - added traffic in megabytes
     */
    Object[] getLastUpdateAndTraffic(final int userId);

    /**
     * Sets new lastUpdate date and used traffic in megabytes in data storage
     * @param userId - primary key of registered user
     * @param newLastUpdate - date of last added files
     * @param newTraffic - used traffic in megabytes
     */
    void setLastUpdateAndTraffic(final int userId, final Date newLastUpdate, final int newTraffic);
}
