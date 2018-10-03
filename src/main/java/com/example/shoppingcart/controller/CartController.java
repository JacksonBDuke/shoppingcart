package com.example.shoppingcart.controller;

import com.example.shoppingcart.model.CartEntry;
import com.example.shoppingcart.repository.CartRepository;
import com.example.shoppingcart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    ProductRepository productRepository;

    @GetMapping()
    public List<CartEntry> listAll() {
        return cartRepository.findAll();
    }

    @PostMapping()
    public CartEntry addToCart(@RequestBody CartEntry ce) {
        return cartRepository.saveAndFlush(ce);
    }
}
