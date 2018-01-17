package com.example.parij.myschoolcomm.Models;

import java.io.Serializable;

/**
 * Created by Chandan Singh on 15-Jan-18.
 */

public class Admin implements Serializable {
    private String username;
    private String password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Admin() {
        //Do not remove, removal may cause World War 3
        username = "";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
