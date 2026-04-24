package com.example.demo.Controller;

import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Model.Order;
import com.example.demo.Model.OrderStatus;
import com.example.demo.Model.PaymentStatus;
import com.example.demo.Model.Product;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Service.OrderService;
import com.example.demo.Service.ProductService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.nio.channels.MulticastChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/admin")
// @PreAuthorize("hasRole('ADMIN')")
@PreAuthorize("hasAuthority('ADMIN')")

public class AdminOrderController {
   private final ProductService productService;
    private OrderService orderService;
     private OrderRepository orderRepository;
    private ProductRepository repo;
    public AdminOrderController(
            OrderService orderService,
            OrderRepository orderRepository,ProductService productService,ProductRepository repo) {

        this.orderService = orderService;
        this.orderRepository = orderRepository;
            this.productService = productService;
            this.repo = repo;
    }
    @GetMapping("/users/{userId}/orders")
    public List<Order> getOrders(@PathVariable Long userId) {
      System.out.println("ADMIN API CALLED userId = " + userId);
          
       List<Order> list =
        orderService.getOrdersByuserId(userId);

    System.out.println("ORDERS SIZE from Controller = " + list.size());

    return list;
    }
    

    @PutMapping("/orders/{orderId}/ship")
    public Order shipOrder(@PathVariable Long orderId){
      Order order=orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found!"));
      if(order.getPaymentStatus()!=PaymentStatus.SUCCESS){
        throw new RuntimeException(
          "Cannot Ship Unpaid Order!"
        );
      } 
      if(order.getOrderStatus()!=OrderStatus.CONFIRMED){
        throw new RuntimeException(
          "Order Not Confirmed!"
        );
      }

      return orderService.shipOrder(orderId);
    }
     @PutMapping("/orders/{orderId}/cancel")
  public Order cancelOrder(@PathVariable Long orderId){
    Order order=orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found!"));
    
    
    return orderService.cancelOrder(orderId);
  }
  @PutMapping("/orders/{orderId}/deliver")
  public Order deliverOrder(@PathVariable Long orderId){
    
    return orderService.deliverOrder(orderId);
  }


  @PostMapping("/add")
    public Product add(@RequestParam String name,@RequestParam double price,@RequestParam String description,@RequestParam String category,@RequestParam("image") MultipartFile file) throws IOException{
       
       String filename=UUID.randomUUID()+"_"+ file.getOriginalFilename();
       Path path=Paths.get("upload");
       if(!Files.exists(path)){
       Files.createDirectories(path);
       }
       Path filePath=path.resolve(filename);
    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
    
      // Files.write(filePath,file.getBytes());
        Product product = new Product();
        System.out.println("NAME = " + name);
System.out.println("PRICE = " + price);
System.out.println("DESC = " + description);
System.out.println("FILE = " + file.getOriginalFilename());
System.out.println("CATEGORY = " + category);
    product.setName(name);
    product.setPrice(price);
    product.setDescription(description);
    product.setCategory(category);
    product.setImageUrl(filename);
        return productService.addProduct(product);
    }
     @DeleteMapping("/delete/{product_id}")
    public String deleteProduct(@PathVariable Long product_id){
        productService.deleteProduct(product_id);
        return "Product Deleted!";
    }
@PostMapping("/update/{product_id}")
public Product updateProduct(@PathVariable Long product_id, @RequestParam String name,
    @RequestParam double price, @RequestParam String description, @RequestParam String category,
    @RequestParam("image") MultipartFile file 
) throws IOException{
   Product product2=productService.getByIProduct(product_id);  
    product2.setName(name);
    product2.setPrice(price);

    product2.setDescription(description);
    product2.setCategory(category);
    if(file!=null && !file.isEmpty()){
        String filename=UUID.randomUUID()+"_"+ file.getOriginalFilename();
       Path path=Paths.get("upload");
       if(!Files.exists(path)){
       Files.createDirectories(path);
       }
       Path filePath=path.resolve(filename);
     Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
    product2.setImageUrl(filename);     

}
return productService.addProduct(product2);
}
}
