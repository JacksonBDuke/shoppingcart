package com.example.shoppingcart.controller;

import com.example.shoppingcart.model.Product;
import com.example.shoppingcart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping()
    public List<Product> listAll() {
        return productRepository.findAll();
    }


    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id){
        return productRepository.findById(id).get();
    }

    @PostMapping()
    public Product newProduct(@RequestBody Product product) {
        return productRepository.saveAndFlush(product);
    }
}
