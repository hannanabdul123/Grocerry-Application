package com.example.demo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Order;
import com.example.demo.Service.OrderService;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

   public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping("/place")
    public Order placeOrder(Authentication authentication){
     
   String email=authentication.getName();

  return orderService.placeOrder(email);
    }
     // 🔹 Get logged-in user's orders
    @GetMapping("/my")
    public List<Order> getMyOrders(Authentication authentication) {
        String email = authentication.getName();
        return orderService.getOrders(email);
        
    }
    
}
