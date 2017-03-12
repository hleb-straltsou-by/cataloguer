package com.gv.cataloguer.models;

/**
 * specifies properties of user in authenthication system
 */
public class User {

    /** property - id of user */
    private int userId = 0;

    /** property - login of user */
    private String login = "";

    /** property - name of user */
    private String name = "";
    private Role role;

    public User(String name, Role role) {
        this.name = name;
        this.role = role;
    }

    public User(int userId, String login, String name, Role role) {
        this.userId = userId;
        this.login = login;
        this.name = name;
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }
}
