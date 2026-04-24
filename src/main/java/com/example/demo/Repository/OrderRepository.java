package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Model.Order;
import com.example.demo.Model.User;
public interface OrderRepository extends JpaRepository<Order,Long>{
    List<Order> findByUser_userEmail(String userEmail);
    @Modifying
    @Transactional
    void deleteByUser(User user);
     
    @Query("SELECT o FROM Order o WHERE o.user.userId = :userId")
    List<Order> getOrdersByuserId(@Param("userId") Long userId);
    //List<Order> findByUser_userId(Long userId);
}
