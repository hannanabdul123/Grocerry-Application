package com.example.demo.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Model.LoginRequest;
import com.example.demo.Model.User;
import com.example.demo.Security.JwtUtil;
import com.example.demo.Service.UserService;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final JwtUtil jwtUtil;
    
    private UserService userService;
    // Constructor Injection
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser=userService.createUser(user);
        return new ResponseEntity<>(savedUser,HttpStatus.CREATED);
    }
    
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
       User user=userService.login(
        loginRequest.getUserEmail(),
        loginRequest.getPassword()
    );

       return jwtUtil.generateToken(user.getUserEmail());
    }
    

}
