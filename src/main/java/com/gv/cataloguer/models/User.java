package com.gv.cataloguer.models;

public class User {
    private int userId = 0;
    private String login = "";
    private String name = "";
    private Role role;

    public User(Role role) {
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
