package com.example.demo.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Model.Cart;
import com.example.demo.Model.Order;

import com.example.demo.Model.OrderItem;
import com.example.demo.Model.OrderStatus;
import com.example.demo.Model.PaymentStatus;
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
  private final InvoiceService invoiceService;

    public OrderService(OrderRepository orderRepository,
                        CartRepository cartRepository,
                        UserRepository userRepository,InvoiceService invoiceService) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.invoiceService=invoiceService;
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
             
               order.setOrderStatus(OrderStatus.PLACED);
               order.setPaymentStatus(PaymentStatus.PENDING);
               
                //Create Orderitems with Valid order_id

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
                 
                Order finalOrder=orderRepository.save(order); 
                 
                 cartRepository.deleteByUser_userEmail(email);
              
          return finalOrder;
  }
  public List<Order> getOrders(String email){
    return orderRepository.findByUser_userEmail(email);
  
}
public List<Order> getOrdersByuserId(Long userId){
       System.out.println("UserId: "+userId);
    List<Order> list =
        orderRepository.getOrdersByuserId(userId);

    System.out.println("Orders size from service= " + list.size());
 List<Order> all =
        orderRepository.findAll();

    System.out.println("All orders = " + all.size());
    return list;
}

public Order makePaymentDone(Long orderId){
  Order order=orderRepository.findById(orderId)
  .orElseThrow(()-> new RuntimeException("Order not found"));

  //Status will change here
  order.setPaymentStatus(PaymentStatus.SUCCESS);
  order.setOrderStatus(OrderStatus.CONFIRMED);
  
    orderRepository.save(order);
  invoiceService.generatInvoice(order);
  return order;
}
public Order shipOrder(Long orderId){
  Order order=orderRepository.findById(orderId)
              .orElseThrow(()-> new RuntimeException("Order not found!"));
              order.setOrderStatus(OrderStatus.SHIPPED);
              return orderRepository.save(order);
}
public Order deliverOrder(Long orderId){
 Order order=orderRepository.findById(orderId)
              .orElseThrow(()-> new RuntimeException("Order not found!"));
              
              if(order.getPaymentStatus()!=PaymentStatus.SUCCESS){
                throw new RuntimeException("UnPaid order cannot be Deliver!");
              }
              if(order.getOrderStatus()!=OrderStatus.SHIPPED){
                throw new RuntimeException("Order should be shipped first!");
              }
             order.setOrderStatus(OrderStatus.DELIVERED);
             return orderRepository.save(order);
}
public Order cancelOrder(Long orderId){

  Order order=orderRepository.findById(orderId)
              .orElseThrow(()-> new RuntimeException("Order not found!"));
              if(order.getOrderStatus()==OrderStatus.DELIVERED||order.getOrderStatus()==OrderStatus.SHIPPED){
                throw new RuntimeException("Order already processed!");
              }
             
              order.setOrderStatus(OrderStatus.CANCELLED);
              return orderRepository.save(order);
              
}

}
