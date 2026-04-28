package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PageController {

@GetMapping("/")
public String homePage() {
    return "home";
}

 @GetMapping("/signup")
    public String signupPage() {
        return "signup";   // login.jsp
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";   // login.jsp
    }
   

    @GetMapping("/products")
    public String productsPage() {
        return "products";
    }

    @GetMapping("/cart")
    public String cartPage() {
        return "cart";
    }

    @GetMapping("/orders")
    public String ordersPage() {
        return "orders";
    }

    @GetMapping("/invoice")
    public String invoicePage() {
        return "invoice";
    }
    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }
    @GetMapping("/admin-user")
    public String adminUser() {
        return "admin-user";
    }

    @GetMapping("/forgot-password")
    public String forgotPage() {
        return "forgot-password"; // forgot-password.jsp
    }

    @GetMapping("/reset-password")
    public String resetPage() {
        return "reset-password"; // reset-password.jsp
    }
    @GetMapping("/message")
    public String openChatPage() {
        return "message"; // message.jsp
    }
   
    
    
}
