package com.gv.cataloguer.authenthication.dao;

import com.gv.cataloguer.models.User;
import java.sql.SQLException;
import java.util.Date;

public interface UserDao {

    User getUser(String login, String password) throws SQLException, ClassNotFoundException;

    Object[] getLastUpdateAndTraffic(int userId);

    void setLastUpdateAndTraffic(int userId, Date newLastUpdate, int newTraffic);
}
