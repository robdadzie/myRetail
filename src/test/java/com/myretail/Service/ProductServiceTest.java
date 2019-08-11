package com.myretail.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.core.Domain.ProductPrice;
import com.myretail.core.Domain.Product;
import com.myretail.core.service.ProductServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.client.ExpectedCount.manyTimes;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

  @Spy
  private ProductServiceImpl productService;

  private static final String productUrl = "https://foo.com/bar/{productId}";
  private Product product;
  private ProductPrice pdtPrice;

  @Before
  public void setup() {

    pdtPrice = new ProductPrice();
    pdtPrice.setValue(new BigDecimal(5.0122));
    pdtPrice.setCurrencyCode(Currency.getInstance("USD"));

    product = new Product();
    product.setId("11223344");
    product.setUpc("2344-yt6-667");
    product.setTitle("Product Numbero uno");
    product.setProductClass("Luxury Item");
    product.setPrice(pdtPrice);
  }

  @Test
  public void testGetProductDetails() throws JsonProcessingException {
    doReturn(product).when(productService).getProductDetails(product.getId()) ;
    doReturn(pdtPrice).when(productService).getProductPrice(product.getId());

    Product result = productService.getProductDetails(product.getId());
    Assert.assertEquals(new ObjectMapper().writeValueAsString(product),
                        new ObjectMapper().writeValueAsString(result));

  }

  @Test
  public void testRestServer() throws JsonProcessingException {
    Map<String, String> urlParams = new HashMap<>();
    urlParams.put("productId", product.getId());

    RestTemplate restTemplate = new RestTemplate();
    MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();

    server.expect(manyTimes(), requestTo("https://foo.com/bar/11223344"))
          .andExpect(method(HttpMethod.GET))
          .andRespond(withSuccess(new ObjectMapper().writeValueAsString(product), MediaType.APPLICATION_JSON));

    restTemplate.getForObject(productUrl, String.class, urlParams);
    server.verify();
  }
}
