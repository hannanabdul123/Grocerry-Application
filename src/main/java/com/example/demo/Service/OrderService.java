package com.example.demo.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Model.Cart;
import com.example.demo.Model.Order;

import com.example.demo.Model.OrderItem;
import com.example.demo.Model.Product;
import com.example.demo.Model.User;
import com.example.demo.Repository.CartRepository;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.UserRepository;

import java.util.stream.Collectors;

@Service

public class OrderService {
  private final OrderRepository orderRepository;
  private final CartRepository cartRepository;
  private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository,
                        CartRepository cartRepository,
                        UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    } 
@Transactional
  public Order placeOrder(String email){
      User user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found")); 
   
   List<Cart> cartItems = cartRepository.findByUser_userEmail(email);
                if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
    double total=cartItems.stream()
                 .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice())
                 .sum();

                 Order order=new Order();
                order.setUser(user); 
                 order.setTotalAmount(total);
                 order.setOrderDate(LocalDateTime.now());
        
                 List<OrderItem> orderitems=cartItems.stream().map(cart->
                  {
                    OrderItem item=new OrderItem();
                    item.setProductName(cart.getProduct().getName());
                    item.setPrice(cart.getProduct().getPrice());
                    item.setQuantity(cart.getQuantity());

                     item.setOrder(order);
                 return item;
                  }
                 ).collect(Collectors.toList());


                 order.setOrderItems(orderitems);
      
                 //Save Order
                Order savedOrder=orderRepository.save(order); 
                 
                 //Clear Cart
                 cartRepository.deleteByUser_userEmail(email);
              
          return savedOrder;
  }
  public List<Order> getOrders(String email){
    return orderRepository.findByUser_userEmail(email);
  
}
}
