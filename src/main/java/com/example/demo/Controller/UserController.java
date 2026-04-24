package com.example.demo.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Model.LoginRequest;
import com.example.demo.Model.User;
import com.example.demo.Model.DTO.ChangePasswordRequest;
import com.example.demo.Model.DTO.ForgotPasswordRequest;
import com.example.demo.Model.DTO.LoginResponse;
import com.example.demo.Model.DTO.ResetPasswordRequest;
import com.example.demo.Model.DTO.UpdateProfileRequest;
import com.example.demo.Model.DTO.UserProfileResponse;
import com.example.demo.Security.JwtUtil;
import com.example.demo.Service.PasswordResetService;
import com.example.demo.Service.UserService;
import javax.validation.Valid;



@RestController
@RequestMapping("/api/users")
public class UserController {

    private final JwtUtil jwtUtil;
  
    private UserService userService;
    private PasswordResetService passwordResetService;
    // Constructor Injection
    public UserController(UserService userService, JwtUtil jwtUtil,PasswordResetService passwordResetService) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
     this.passwordResetService=passwordResetService;
    }

    @GetMapping("/me")

    public UserProfileResponse getProfile(Authentication auth) {
        return userService.getMyProfile(auth.getName());
    }

    @PostMapping("/me")
    public String updateProfile(Authentication auth, @RequestBody UpdateProfileRequest req) {
        userService.updateProfile(auth.getName(), req);
        return "Profile updated successfully";
    }

    // -----------Password------------

    @PutMapping("/change-password")
    public String changePassword(Authentication auth, @RequestBody ChangePasswordRequest req) {
        userService.changePassword(auth.getName(), req);
        return "Password changed successfully";
    }

    

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
     User savedUser = userService.createUser(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    
        //     if(!user.getUserEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")){
    //         return ResponseEntity
    //                .badRequest()
    //                .body("Email is not valid!");
    //     }
    //     try{
    //     User savedUser = userService.createUser(user);
    //     return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    //    }catch(RuntimeException ex){
    //     return ResponseEntity
    //            .status(HttpStatus.BAD_REQUEST)
    //             .body(ex.getMessage());
    //    }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        System.out.println("EMAIL = " + loginRequest.getUserEmail());
        System.out.println("PASSWORD = " + loginRequest.getPassword());
        try {
            User user = userService.login(
                    loginRequest.getUserEmail(),
                    loginRequest.getPassword());
            String token = jwtUtil.generateToken(user.getUserEmail(), user.getRole().name());
            LoginResponse reponse = new LoginResponse(token, user.getName(), user.getRole().name(),user.getUserId());
            return ResponseEntity.ok(reponse);

        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(401) // unauthorized
                    .body(e.getMessage());
        } catch (Exception e) {
            // any other unexpected error
            return ResponseEntity
                    .status(500) // Internal Server Error
                    .body("Something went wrong, please try again later.");
        }

    }
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest req) {
     
     try{
           String email=req.getUserEmail();
       System.out.println("EMAIL1 = " + email); 
        passwordResetService.forgotPassword(email);
        System.out.println("EMAIL2 = " + email); 
       return ResponseEntity.ok("Reset Link sent!"); 
    } catch (NullPointerException e) {
        return ResponseEntity.status(500).body("Internal server error: Service issue");
    }
    catch(RuntimeException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest req) {
         try{
       String token=req.getToken();
       String newpassword=req.getNewPassword();
        passwordResetService.resetPassword(token, newpassword);
       return ResponseEntity.ok("Password updated successfully!");
         }catch (NullPointerException e) {
           return ResponseEntity.status(500).body("Internal server error: Service issue");
         }
         catch(RuntimeException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
        
    }
    
    

}
