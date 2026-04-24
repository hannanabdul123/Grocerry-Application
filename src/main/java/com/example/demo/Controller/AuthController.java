package com.example.demo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.PasswordResetService;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/auth")
public class AuthController {
    private final PasswordResetService resetService;
    public AuthController(PasswordResetService resetService){
        this.resetService=resetService;
    }
    @PostMapping("/forgot-password")
    public String forgot(@RequestParam String email) {
        resetService.forgotPassword(email);
        return "Password reset link sent";
    }
    @PostMapping("/reset-password")
    public String reset(@RequestParam String token,@RequestParam String newPassword) {
        resetService.resetPassword(token,newPassword);
         return "Password updated successfully";
    }
    
    
}
