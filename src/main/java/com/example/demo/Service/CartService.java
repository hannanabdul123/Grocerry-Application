package com.example.demo.Service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Model.Cart;
import com.example.demo.Repository.CartRepository;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart addToCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public List<Cart> getCartItems(String email) {
        return cartRepository.findByUser_userEmail(email);
    }

    public void clearCart(String email) {
        cartRepository.deleteByUser_userEmail(email);
    }
}
