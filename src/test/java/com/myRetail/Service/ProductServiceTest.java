package com.myRetail.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myRetail.Data.Item;
import com.myRetail.Data.Price;
import com.myRetail.Data.Product;
import com.myRetail.Data.ProductDescription;
import com.myRetail.Data.ProductDetails;
import com.myRetail.Data.ProductInfoWrapper;
import com.myRetail.service.ProductServiceImpl;
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

  private static final String productUrl = "https://foo.com/bar/{productId}?query";
  private ProductDescription pdtDesc;
  private Item               item;
  private Product            productInfo;
  private Price              pdtPrice;

  @Before
  public void setup() {
    pdtDesc = new ProductDescription();
    pdtDesc.setTitle("Product Numbero uno");
    pdtDesc.setGeneralDescription("Best product ever!");

    item = new Item();
    item.setItemId("11223344");
    item.setUpc("2344-yt6-667");
    item.setDescription(pdtDesc);

    productInfo = new Product();
    productInfo.setItem(item);

    pdtPrice = new Price();
    pdtPrice.setValue(new BigDecimal(5.0122));
    pdtPrice.setCurrencyCode(Currency.getInstance("USD"));

  }

  @Test
  public void testGetProductDetails() throws JsonProcessingException {
    ProductDetails expectedResult = new ProductDetails(productInfo, pdtPrice);
    doReturn(productInfo).when(productService).getProductInformation(item.getItemId());
    doReturn(pdtPrice).when(productService).getProductPrice(item.getItemId());

    ProductDetails productDetails = productService.getProductDetails(item.getItemId());
    Assert.assertEquals(new ObjectMapper().writeValueAsString(expectedResult),
                        new ObjectMapper().writeValueAsString(productDetails));

  }

  @Test
  public void testFindProductInformation() throws JsonProcessingException {
    ProductInfoWrapper pw = new ProductInfoWrapper(productInfo);

    Map<String, String> urlParams = new HashMap<>();
    urlParams.put("productId", item.getItemId());

    RestTemplate          restTemplate = new RestTemplate();
    MockRestServiceServer server       = MockRestServiceServer.bindTo(restTemplate).build();

    server.expect(manyTimes(), requestTo("https://foo.com/bar/11223344?query"))
          .andExpect(method(HttpMethod.GET))
          .andRespond(withSuccess(new ObjectMapper().writeValueAsString(pw), MediaType.APPLICATION_JSON));

    restTemplate.getForObject(productUrl, ProductInfoWrapper.class, urlParams);
    server.verify();

  }
}
