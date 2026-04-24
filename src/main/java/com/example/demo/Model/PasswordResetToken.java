package com.example.demo.Model;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.persistence.Id;

@Entity
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String token;
   private LocalDateTime expiryTime;

   @OneToOne
   @JoinColumn(name = "user_id")

   private User user;
   
   public PasswordResetToken(){

   }
   

   public Long getId() {
    return id;
   }

   public void setId(Long id) {
    this.id = id;
   }

   public String getToken() {
    return token;
   }

   public void setToken(String token) {
    this.token = token;
   }

   public LocalDateTime getExpiryTime() {
    return expiryTime;
   }

   public void setExpiryTime(LocalDateTime expiryTime) {
    this.expiryTime = expiryTime;
   }

   public User getUser() {
    return user;
   }

   public void setUser(User user) {
    this.user = user;
   }

   
}
