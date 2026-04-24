package com.example.demo.Service;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Model.Cart;
import com.example.demo.Model.User;
import com.example.demo.Model.Product;
import com.example.demo.Repository.CartRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Repository.ProductRepository;
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository,UserRepository userRepository,ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository=userRepository;
        this.productRepository=productRepository;
    }

    public Cart addToCart(User user, Product product, int quantity) {
        Optional<Cart> existing=cartRepository.findByUserAndProduct(user, product);
        if(existing.isPresent()){
            Cart cart=existing.get();
            cart.setQuantity(cart.getQuantity()+quantity);
        return cartRepository.save(cart);
        }else{
            Cart cart = new Cart();
        cart.setUser(user);
        cart.setProduct(product);
        cart.setQuantity(quantity);

        return cartRepository.save(cart);
        }
    }

    public List<Cart> getCartItems(String email) {
        return cartRepository.findByUser_userEmail(email);
    }
    @Transactional
    public void clearCart(String email) {
        cartRepository.deleteByUser_userEmail(email);
    }
    
    public Cart updateQuantity(String email, Long productId, int qty) {

    User user = userRepository.findByUserEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

    Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));

    Cart cart = cartRepository
            .findByUserAndProduct(user, product)
            .orElseThrow(() -> new RuntimeException("Cart not found"));

    // ✅ qty = 0 → remove only this product
    if (qty <= 0) {

        cartRepository.delete(cart);
        return null;

    }

    // ✅ qty > 0 → update only this product
    cart.setQuantity(qty);

    return cartRepository.save(cart);
}
}