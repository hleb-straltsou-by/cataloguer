package com.gv.cataloguer.models;

import org.junit.Test;

import javax.sound.midi.Soundbank;

public class UserTest {
    @Test
    public void UserInitTest() throws Exception {
        User user = new User(1, "vi@gmail.com", "Veronica Sanko", Role.DEFAULT);
        System.out.println("userId: " + user.getUserId());
        System.out.println("login: " + user.getLogin());
        System.out.println("name: " + user.getName());
        System.out.println("role: " + user.getRole().toString());
    }
}