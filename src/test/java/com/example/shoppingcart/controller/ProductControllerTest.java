package com.example.shoppingcart.controller;

import com.example.shoppingcart.model.Product;
import com.example.shoppingcart.repository.ProductRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ProductControllerTest {

    private static String resourceUrl = "http://localhost:8080/api/v1/product";
    private static final long TEST_PID = 10L;
    private static final String TEST_NAME = "Test Banana";
    private static final String TEST_DESC = "Test Yellow";
    private static final double TEST_PRICE = 1111d;

    private static Product product;
    private static List<Product> productList;

    @Autowired
    TestRestTemplate testRestTemplate;

    @MockBean
    ProductRepository productRepository;

    @Before
    public void init() {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testListAll() throws Exception {

        Product product = new Product();
        product.setPID(TEST_PID);
        product.setName(TEST_NAME);
        product.setDescription(TEST_DESC);
        product.setPrice(TEST_PRICE);

        List<Product> productList = new ArrayList<>();
        productList.add(product);

        when(productRepository.findAll()).thenReturn(productList);

        ResponseEntity<String> response = testRestTemplate.exchange(resourceUrl, HttpMethod.GET, null, String.class);

        ObjectMapper mapper = new ObjectMapper();
        List<Product> responseList = mapper.readValue(response.getBody(), new TypeReference<List<Product>>(){ });

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        productAssertion(productList.get(0));
    }

    @Test
    public void testGetProductId() throws Exception {

        Product product = new Product();
        product.setPID(TEST_PID);
        product.setName(TEST_NAME);
        product.setDescription(TEST_DESC);
        product.setPrice(TEST_PRICE);

        when(productRepository.findById(mock(Long.class)).get()).thenReturn(product);

        RequestEntity<Product> request = RequestEntity
                .post(new URI(resourceUrl))
                .accept(MediaType.APPLICATION_JSON)
                .body(product);
        ResponseEntity<String> response = testRestTemplate.exchange(request, String.class);

        ObjectMapper mapper = new ObjectMapper();
        Product responseProduct = mapper.readValue(response.getBody(), Product.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        productAssertion(responseProduct);
    }

    private void productAssertion(Product p){
        assertThat(p.getPID(), is(TEST_PID));
        assertThat(p.getName(), is(TEST_NAME));
        assertThat(p.getDescription(), is(TEST_DESC));
        assertThat(p.getPrice(), is(TEST_PRICE));
    }
}

