package com.gv.cataloguer.authenthication.dao;

import com.gv.cataloguer.models.User;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface UserDao {

    User getUser(String login, String password) throws SQLException, ClassNotFoundException;

    List<String> getAllUserEmails();

    Object[] getLastUpdateAndTraffic(int userId);

    void setLastUpdateAndTraffic(int userId, Date newLastUpdate, int newTraffic);
}
