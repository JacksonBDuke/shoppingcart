package com.example.shoppingcart.controller;

import com.example.shoppingcart.model.CartEntry;
import com.example.shoppingcart.model.Product;
import com.example.shoppingcart.repository.CartRepository;
import com.example.shoppingcart.repository.ProductRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CartControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;

//    @Value("${API.v1.CART_PREFIX}")
//    private String cartPrefix;
    private String cartPrefix = "/cart";


//    @Value("${API.v1.API_PREFIX")
//    private String apiPrefix;
    private String apiPrefix = "/api/v1";
    private String resourceUrl = "http://localhost:8080" + apiPrefix + cartPrefix;

    @Mock
    List<CartEntry> cart;

    @Test
    public void testListAll() throws Exception {

        CartEntry ce = new CartEntry();
        ce.setPID(1L);
        ce.setQuantity(1);

        ResponseEntity<String> response = testRestTemplate.exchange(resourceUrl, HttpMethod.GET, null, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Should be same", 1L, root.get("PID").asLong());
        assertEquals(1, root.get("quantity").asInt());
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
