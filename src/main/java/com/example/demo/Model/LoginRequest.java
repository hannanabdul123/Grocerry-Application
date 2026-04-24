package com.example.demo.Model;
import com.fasterxml.jackson.annotation.JsonProperty;
public class LoginRequest {

    @JsonProperty("userEmail")
    private String userEmail;
     @JsonProperty("password")
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

