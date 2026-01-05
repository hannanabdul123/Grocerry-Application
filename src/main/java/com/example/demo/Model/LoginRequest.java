package com.example.demo.Model;

public class LoginRequest {

    private String userEmail;
    private String password;

    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String email) {
        this.userEmail = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}

