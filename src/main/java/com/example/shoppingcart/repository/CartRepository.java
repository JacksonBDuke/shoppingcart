package com.example.shoppingcart.repository;

import com.example.shoppingcart.model.CartEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntry, Long> {
}
