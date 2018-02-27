package com.example.laptop.restrict.Model;

import java.io.Serializable;

/**
 * Created by ivandjordjevic on 26.2.18..
 */

public class LoginRequest implements Serializable {

    private String email, password;

    public LoginRequest() {

    }

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
