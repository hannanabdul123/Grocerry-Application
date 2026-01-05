package com.example.demo.Service;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Model.User;
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    @Override
    public User login(String email,String password){
        Optional<User> optionalUser=userRepository.findByUserEmail(email);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("User Not Found");  
        }
        User user=optionalUser.get();
        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new RuntimeException("Invalid Password!");
        }
        return user;// login successful

    }

}
