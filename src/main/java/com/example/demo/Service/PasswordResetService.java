package com.example.demo.Service;

import java.time.LocalDateTime;
import java.util.UUID;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Model.PasswordResetToken;
import com.example.demo.Model.User;
import com.example.demo.Repository.PasswordResetTokenRepository;
import com.example.demo.Repository.UserRepository;
@Service
public class PasswordResetService {
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepo;
    private final PasswordEncoder passwordEncoder;
    public PasswordResetService(UserRepository userRepository,
            PasswordResetTokenRepository tokenRepo,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenRepo = tokenRepo;
        this.passwordEncoder = passwordEncoder;
    }
    
    public void forgotPassword(String userEmail){
        System.out.println("Email in Service: "+userEmail);
        User user=userRepository.findByUserEmail(userEmail)
        .orElseThrow(()-> new RuntimeException("User not found"));
        String token=UUID.randomUUID().toString();
        PasswordResetToken resetToken=new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryTime(LocalDateTime.now().plusMinutes(15));
        tokenRepo.save(resetToken);
   String link = "http://localhost:8080/reset-password?token=" + token;

System.out.println("RESET LINK: " + link);

    }
    public void resetPassword(String token, String newPassword){
        PasswordResetToken ResetToken=tokenRepo.findByToken(token)
                     .orElseThrow(()-> new RuntimeException("Invalid Token!"));
                     if(ResetToken.getExpiryTime().isBefore(LocalDateTime.now())){
                        throw new RuntimeException("Token Expired!");
                     }

                     User user=ResetToken.getUser();
                     user.setPassword(passwordEncoder.encode(newPassword));
                     userRepository.save(user);

                     tokenRepo.delete(ResetToken);


    }

}
