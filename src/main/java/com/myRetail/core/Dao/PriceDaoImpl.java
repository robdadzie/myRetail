package com.myRetail.core.Dao;

import com.myRetail.core.Domain.ProductPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
@SuppressWarnings("unchecked")
public class PriceDaoImpl implements PriceDao {
    private static final String PRICE = "ProductPrice";

    private RedisTemplate<String, ProductPrice> redisTemplate ;
    private HashOperations hashOperations;

    @Autowired
    public PriceDaoImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void saveOrUpdatePrice(String productId, ProductPrice price) {
        hashOperations.put(PRICE, productId, price);
    }

    @Override
    public ProductPrice findPriceByProductId(String productId){
        return (ProductPrice) hashOperations.get(PRICE, productId);
    }
}
