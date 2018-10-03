package com.example.shoppingcart.controller;

import com.example.shoppingcart.model.Product;
import com.example.shoppingcart.repository.ProductRepository;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;

public class ProductControllerTest {

    private static String resourceUrl = "http://localhost:8080/api/v1/cart";

    @Mock
    ProductRepository productRepository;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void testListAll() {
        List<Product> productlist = new ArrayList<>();
        Product product = new Product();
        product.setPID(3);
        product.setName("TestProduct");
        product.setDescription("A very tasty test");
        product.setPrice(3.0d);
        productlist.add(product);

        when(productRepository.findAll()).thenReturn(productlist);
        HttpEntity<String> request = new HttpEntity("");
        ResponseEntity<List> response = testRestTemplate.exchange(resourceUrl, HttpMethod.GET, request, List.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(notNullValue()));
    }
}
