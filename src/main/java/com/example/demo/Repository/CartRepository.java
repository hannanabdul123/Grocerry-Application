package com.example.demo.Repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.Model.Cart;
import com.example.demo.Model.User;
import com.example.demo.Model.Product;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUser_userEmail(String userEmail);

    void deleteByUser_userEmail(String userEmail);
    @Modifying
    @Transactional
    void deleteByUser(User user);
      Optional<Cart> findByUserAndProduct(User user, Product product);
}

