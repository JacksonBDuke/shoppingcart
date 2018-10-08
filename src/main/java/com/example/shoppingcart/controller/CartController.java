package com.example.shoppingcart.controller;

import com.example.shoppingcart.model.CartEntry;
import com.example.shoppingcart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    @Autowired
    CartRepository cartRepository;

    @GetMapping()
    public List<CartEntry> listAll() {
        return cartRepository.findAll();
    }

    @PostMapping()
    public ResponseEntity<CartEntry> addToCart(@RequestBody CartEntry ce) {
        if(ce != null){
            ce = cartRepository.saveAndFlush(ce);
        }
        return new ResponseEntity<>(ce, HttpStatus.OK);
    }
}
