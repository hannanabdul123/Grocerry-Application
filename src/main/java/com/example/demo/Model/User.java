package com.example.demo.Model;


import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String userEmail;

    @Column(nullable = false)
    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(nullable = false)
    private String role; // ADMIN / USER
    

    public User(){

    }

    public User(String name, String email, String password, String role) {
        this.name = name;
        this.userEmail = email;
        this.password = password;
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    

}
