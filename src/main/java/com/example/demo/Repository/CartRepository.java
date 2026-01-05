package com.example.demo.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Cart;


public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUser_userEmail(String userEmail);

    void deleteByUser_userEmail(String userEmail);
}

