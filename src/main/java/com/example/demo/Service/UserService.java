package com.example.demo.Service;



import com.example.demo.Model.User;

public interface UserService  {
    User createUser( User user);
    User login(String email,String password);
}
