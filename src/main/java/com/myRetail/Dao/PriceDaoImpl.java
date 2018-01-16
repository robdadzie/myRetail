package com.myRetail.Dao;

import com.myRetail.Data.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
@SuppressWarnings("unchecked")
public class PriceDaoImpl implements PriceDao {
    private static final String PRICE = "Price";

    private RedisTemplate<String, Price> redisTemplate ;
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
    public void saveOrUpdatePrice(String productId, Price price) {
        hashOperations.put(PRICE, productId, price);
    }

    @Override
    public Price findPriceByProductId(String productId){
        return (Price) hashOperations.get(PRICE, productId);
    }
}
