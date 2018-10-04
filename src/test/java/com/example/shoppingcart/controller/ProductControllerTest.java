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
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ProductControllerTest {

    private static String resourceUrl = "http://localhost:8080/api/v1/product";

    @Mock
    ProductRepository productRepository;

    @Mock
    ProductController productController;

    @Mock
    private List<Product> productList;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testListAll() throws Exception {

        Product product = new Product();
        product.setPID(3);
        product.setName("TestProduct");
        product.setDescription("A very tasty test");
        product.setPrice(3.0d);

        /*
        Make mock list to return with GetAll.
         */

        when(productList.get(0)).thenReturn(product);

        when(productController.listAll()).thenReturn(productList);
//        when(productController.listAll()).thenReturn(productRepository.findAll());

        ResponseEntity<String> response = testRestTemplate.exchange(resourceUrl, HttpMethod.GET, null, String.class);

//        List<Product> resultList = new ObjectMapper().readValue(response.getBody(), new TypeReference<List<Product>>(){});
//        assertThat(resultList.get(0).getPID(), is(3));

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(notNullValue()));
    }
}
