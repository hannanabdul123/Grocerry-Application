package com.example.demo.Controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Model.Cart;
import com.example.demo.Model.Product;
import com.example.demo.Model.User;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartController(CartService cartService,
                          UserRepository userRepository,
                          ProductRepository productRepository) {
        this.cartService = cartService;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    // ➕ Add to cart
    @PostMapping("/add/{productId}")
    public Cart addToCart(@PathVariable Long productId,
                          @RequestParam int quantity,
                          Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setProduct(product);
        cart.setQuantity(quantity);

        return cartService.addToCart(cart);
    }

    // 👀 View cart
    @GetMapping("/my")
    public List<Cart> viewCart(Authentication authentication) {
        return cartService.getCartItems(authentication.getName());
    }
}
