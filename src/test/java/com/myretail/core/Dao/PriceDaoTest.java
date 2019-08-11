package com.myretail.core.Dao;

import com.myretail.core.Domain.Product;
import com.myretail.core.Domain.ProductPrice;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.Currency;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PriceDaoTest {

  @Mock
  private RedisTemplate redisTemplateMock = new RedisTemplate<String, ProductPrice>();

  @Spy
  private PriceDaoImpl priceDao;

  @Mock
  private RedisConnection redisConnectionMock;

  @Mock
  private RedisConnectionFactory redisConnectionFactoryMock;

  @Mock
  private HashOperations hashOperationsMock;

  private ProductPrice productPrice;
  private Product product;

  private static String PRODUCT_ID = "1234-678";


  @Before
  public void setUp() {
    product = new Product();
    product.setId(PRODUCT_ID);

    productPrice = new ProductPrice();
    productPrice.setCurrencyCode(Currency.getInstance("GHC"));
    productPrice.setValue(new BigDecimal(15.50));

    when(redisConnectionFactoryMock.getConnection()).thenReturn(redisConnectionMock);
    when(redisTemplateMock.opsForHash()).thenReturn(hashOperationsMock);
    when(hashOperationsMock.get("ProductPrice", product.getId())).thenReturn(productPrice);
    priceDao = new PriceDaoImpl(redisTemplateMock);
  }

  @Test
  public void testSaveOrUpdatePrice() {
    product.setPrice(productPrice);
    priceDao.saveOrUpdatePrice(product.getId(), productPrice);
    verify(hashOperationsMock, times(1)).put("ProductPrice", product.getId(), productPrice);
  }

  @Test
  public void findPriceByProductId() {
    Assert.assertEquals(productPrice, priceDao.findPriceByProductId(PRODUCT_ID));
  }


}
