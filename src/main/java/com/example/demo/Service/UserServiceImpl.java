package com.example.demo.Service;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Model.User;
import com.example.demo.Model.DTO.ChangePasswordRequest;
import com.example.demo.Model.DTO.UpdateProfileRequest;
import com.example.demo.Model.DTO.UserProfileResponse;
import com.example.demo.Repository.UserRepository;



@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor Injection
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User user) {
        if(userRepository.findByUserEmail(user.getUserEmail()).isPresent()){
            throw new RuntimeException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    @Override
    public User login(String email,String password){
       User user = userRepository.findByUserEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found"));
          
        if (!user.isActive()) {
           throw new RuntimeException("Account is deactivated");
           }   
        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new RuntimeException("Invalid Password!");
        }
        return user;// login successful

    }

    //-----------------------Profile--------------
      @Override
    public UserProfileResponse getMyProfile(String email){
        User user=userRepository.findByUserEmail(email)
        .orElseThrow(() -> new RuntimeException("User not Found!"));
        
        UserProfileResponse dto=new UserProfileResponse();
        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setUserEmail(user.getUserEmail());
        dto.setPhone(user.getPhone());
        dto.setAddress(user.getAddress());
        dto.setCity(user.getCity());
        dto.setPincode(user.getPincode());

        return dto;
    }
      @Override
 public void updateProfile(String email,UpdateProfileRequest req){
    User user=userRepository.findByUserEmail(email)
          .orElseThrow(()->new RuntimeException("User not Found!"));
        user.setName(req.getName());
        user.setPhone(req.getPhone());
        user.setAddress(req.getAddress());
        user.setCity(req.getCity());
        user.setPincode(req.getPincode());

        userRepository.save(user);

 }
   @Override
 public void changePassword(String email,ChangePasswordRequest req){
     User user=userRepository.findByUserEmail(email)
            .orElseThrow(()->new RuntimeException("User not Found!"));

            if(!passwordEncoder.matches(req.getOldPassword(), user.getPassword())){
                throw new RuntimeException("Old password is incorrect");
            }
            user.setPassword(passwordEncoder.encode(req.getNewPassword()));
            userRepository.save(user);
 }     
}
