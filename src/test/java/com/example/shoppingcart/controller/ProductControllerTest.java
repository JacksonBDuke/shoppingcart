package com.example.shoppingcart.controller;

import com.example.shoppingcart.model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ProductControllerTest {

    private static String resourceUrl = "http://localhost:8080/api/v1/product";

    @Autowired
    TestRestTemplate testRestTemplate;

    @Before
    public void init() { MockitoAnnotations.initMocks(this); }

    @Test
    public void testListAll() throws Exception {

        ResponseEntity<String> response = testRestTemplate.exchange(resourceUrl, HttpMethod.GET, null, String.class);

        ObjectMapper mapper = new ObjectMapper();
        List<Product> responseList = mapper.readValue(response.getBody(), new TypeReference<List<Product>>(){ });

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertNotNull(responseList.get(0).getPID());
    }
}
