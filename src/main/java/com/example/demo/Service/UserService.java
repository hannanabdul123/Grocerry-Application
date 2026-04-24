package com.example.demo.Service;



import com.example.demo.Model.User;
import com.example.demo.Model.DTO.ChangePasswordRequest;
import com.example.demo.Model.DTO.UpdateProfileRequest;
import com.example.demo.Model.DTO.UserProfileResponse;

public interface UserService  {
    User createUser( User user);
    User login(String email,String password);
    UserProfileResponse getMyProfile(String email);
    public void updateProfile(String email,UpdateProfileRequest req);
    public void changePassword(String emai,ChangePasswordRequest req);
}
