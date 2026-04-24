package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.User;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Custom finder method
    Optional<User> findByUserEmail(String UserEmail);
    void deleteById(Long Id);
}