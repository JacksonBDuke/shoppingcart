package com.example.shoppingcart.controller;

import com.example.shoppingcart.model.CartEntry;
import com.example.shoppingcart.model.Product;
import com.example.shoppingcart.repository.CartRepository;
import com.example.shoppingcart.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CartControllerTest {
    private static String resourceUrl = "http://localhost:8080/api/v1/cart";

    @Autowired
    TestRestTemplate testRestTemplate;

    @Mock
    ProductRepository productRepository;

    @Mock
    CartRepository cartRepository;

    @Test
    public void testListAll() throws Exception {

    }

    @Test
    public void testAddToCart() throws Exception {
        CartEntry ce = new CartEntry();
        ce.setPID(1L);

        HttpEntity<CartEntry> request = new HttpEntity<>(ce);
        ResponseEntity<CartEntry> response = testRestTemplate.exchange(resourceUrl, HttpMethod.POST, request, CartEntry.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        assertThat(response.getBody().getPID(), is(1L));
    }
}
