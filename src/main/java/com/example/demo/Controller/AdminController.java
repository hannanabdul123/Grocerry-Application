package com.example.demo.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.UserRepository;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.Model.Order;
import com.example.demo.Model.User;
import com.example.demo.Model.DTO.AdminUserResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.CartRepository;
import com.example.demo.Repository.InvoiceRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping("/api/admin")
// @PreAuthorize("hasRole('ADMIN')")
@PreAuthorize("hasAuthority('ADMIN')")

public class AdminController {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final InvoiceRepository invoiceRepository;

    public AdminController(
            UserRepository userRepository,
            OrderRepository orderRepository,
            CartRepository cartRepository,
            InvoiceRepository invoiceRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @PutMapping("/users/{id}/deactivate")
    public String deactivateUser(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        if (!user.isActive()) {
            return "Already Deactivated!";
        }
        user.setActive(false);
        userRepository.save(user);
        return "User decativated Successfully!";
    }

    @PutMapping("/users/{id}/activate")
    public String activate(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        if (user.isActive()) {
            return "Already Activated!";
        }
        user.setActive(true);
        userRepository.save(user);
        return "User Activated Sccessfully!";
    }

    @GetMapping("/users")
    public List<AdminUserResponse> getAllUsers(Authentication auth) {
        System.out.println("This is" + auth.getAuthorities());
        return userRepository.findAll()
                .stream()
                .map(user -> {

                    try {
                        AdminUserResponse dto = new AdminUserResponse();

                        dto.setUserId(user.getUserId());

                        dto.setName(user.getName());

                        dto.setUserEmail(user.getUserEmail());

                        dto.setRole(user.getRole().name()); // ADMIN

                        dto.setActive(user.isActive());

                        System.out.println("DTO CREATED SUCCESSFULLY");

                        return dto;

                    } catch (Exception e) {
                        System.out.println("catch ke ander ho bhai!");

                        e.printStackTrace();
                        throw e;
                    }
                })

                .collect(Collectors.toList());
    }

    @DeleteMapping("/users/{id}")
    @Transactional
    public ResponseEntity<?> deletedUser(@PathVariable Long id) {
        System.out.println("DELETE API HIT FOR USER ID = " + id);
        ;
        if (!userRepository.existsById(id)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User not found!");
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Order> orders = orderRepository.findByUser_userEmail(user.getUserEmail());
        for (Order order : orders) {
            invoiceRepository.deleteByOrder(order); // 🔥 missing piece
        }
        orderRepository.deleteByUser(user); // pehle child
        cartRepository.deleteByUser_userEmail(user.getUserEmail());

        userRepository.deleteById(id);
        return ResponseEntity.ok("User deleted!");
    }

}
