package com.example.shoppingcart.controller;

import com.example.shoppingcart.model.CartEntry;
import com.example.shoppingcart.repository.CartRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CartControllerTest {

    private final long TEST_ID = 4L;
    private int TEST_QUANTITY = 3;

    @Autowired
    TestRestTemplate testRestTemplate;

//    @Value("${API.v1.CART_PREFIX}")
//    private String cartPrefix;
    private String cartPrefix = "/cart";


//    @Value("${API.v1.API_PREFIX")
//    private String apiPrefix;
    private String apiPrefix = "/api/v1";
    private String resourceUrl = "http://localhost:8080" + apiPrefix + cartPrefix;

    @MockBean
    CartRepository cartRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testListAllCorrectInput() throws Exception {

        CartEntry ce = new CartEntry();
        ce.setPID(TEST_ID);
        ce.setQuantity(TEST_QUANTITY);

        List<CartEntry> cartEntries = new ArrayList<>();
        cartEntries.add(ce);

        when(cartRepository.findAll()).thenReturn(cartEntries);

        ResponseEntity<String> response = testRestTemplate.exchange(resourceUrl, HttpMethod.GET, null, String.class);
        ObjectMapper mapper = new ObjectMapper();
        List<CartEntry> responseList = mapper.readValue(response.getBody(), new TypeReference<List<CartEntry>> () {});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Should be same", TEST_ID, responseList.get(0).getPID());
        assertEquals(TEST_QUANTITY, responseList.get(0).getQuantity());
    }

    @Test
    public void testAddToCart() throws Exception {
        CartEntry ce = new CartEntry();
        ce.setPID(TEST_ID);
        ce.setQuantity(TEST_QUANTITY);

        when(cartRepository.saveAndFlush(mock(CartEntry.class))).thenReturn(ce);

//        HttpEntity<CartEntry> request = new HttpEntity<>(ce);\
//        RequestEntity<CartEntry> request = new RequestEntity<>(ce);
        RequestEntity<CartEntry> request = RequestEntity
                .post(new URI(resourceUrl))
                .accept(MediaType.APPLICATION_JSON)
                .body(ce);
//        ResponseEntity<CartEntry> response = testRestTemplate.exchange(resourceUrl, HttpMethod.POST, request, CartEntry.class);
        ResponseEntity<String> response = testRestTemplate.exchange(request, String.class);

        ObjectMapper mapper = new ObjectMapper();
        CartEntry responseCartEntry = mapper.readValue(response.getBody(), CartEntry.class);


        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        assertThat(responseCartEntry.getPID(), is(TEST_ID));
        assertThat(responseCartEntry.getQuantity(), is(TEST_QUANTITY));
    }
}
