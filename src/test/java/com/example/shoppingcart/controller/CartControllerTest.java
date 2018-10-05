package com.example.shoppingcart.controller;

import com.example.shoppingcart.model.CartEntry;
import com.example.shoppingcart.model.Product;
import com.example.shoppingcart.repository.CartRepository;
import com.example.shoppingcart.repository.ProductRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
import static org.mockito.Mockito.mock;
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

    /*
     ****    CartRepository IS NOT BEING MOCKED CORRECTLY
     */

    @Mock
    CartRepository cartRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testListAll() throws Exception {

        CartEntry ce = new CartEntry();
        ce.setPID(4L);
        ce.setQuantity(3);

        List<CartEntry> cartEntries = new ArrayList<>();
        cartEntries.add(ce);

        when(cartRepository.findAll()).thenReturn(cartEntries);

        ResponseEntity<String> response = testRestTemplate.exchange(resourceUrl, HttpMethod.GET, null, String.class);

        ObjectMapper mapper = new ObjectMapper();
        List<CartEntry> responseList = mapper.readValue(response.getBody(), new TypeReference<List<CartEntry>> () {});
//        JsonNode root = mapper.readTree(response.getBody());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Should be same", 1L, responseList.get(0).getPID());
        assertEquals(1, responseList.get(0).getQuantity());
    }

    @Test
    public void testAddToCart() throws Exception {
        CartEntry ce = new CartEntry();
        ce.setPID(3L);
        ce.setQuantity(4);

        when(cartRepository.saveAndFlush(mock(CartEntry.class))).thenReturn(ce);

        HttpEntity<CartEntry> request = new HttpEntity<>(ce);
        ResponseEntity<CartEntry> response = testRestTemplate.exchange(resourceUrl, HttpMethod.POST, request, CartEntry.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        assertThat(response.getBody().getPID(), is(3L));
    }
}
