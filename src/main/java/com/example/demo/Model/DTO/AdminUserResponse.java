package com.example.demo.Model.DTO;

public class AdminUserResponse {

    private Long userId;
    private String name;
    private String userEmail;
    private String role;
    private boolean active;


public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
 public void setRole(String role) {
    this.role = role;
   }
    public String getRole() { 
    return role;
}
public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String email) {
        this.userEmail = email;
    }

     public boolean isActive() {
    return active;
   }

   public void setActive(boolean active) {
    this.active = active;
   }

    

    // getters & setters
}