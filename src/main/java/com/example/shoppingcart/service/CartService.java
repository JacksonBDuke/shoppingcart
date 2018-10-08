package com.example.shoppingcart.service;

import com.example.shoppingcart.model.CartEntry;
import com.example.shoppingcart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Repository
public class CartService {
    @Autowired
    CartRepository cartRepository;

    @Transactional
    public List<CartEntry> getAll() {
        return cartRepository.findAll();
    }

    @Transactional
    public CartEntry saveAndFlush(CartEntry ce) {
        if( ce != null ) {
            ce = cartRepository.saveAndFlush(ce);
        }
        return ce;
    }
}
