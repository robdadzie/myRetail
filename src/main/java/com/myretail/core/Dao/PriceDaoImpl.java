package com.myretail.core.Dao;

import com.myretail.core.Domain.ProductPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
@SuppressWarnings("unchecked")
public class PriceDaoImpl implements PriceDao {
    private static final String PRODUCT_PRICE = "ProductPrice";

    private RedisTemplate<String, ProductPrice> redisTemplate ;
    private HashOperations hashOperations;

    public PriceDaoImpl() {
    }

    @Autowired
    public PriceDaoImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations =
            (this.redisTemplate != null && this.hashOperations == null) ? this.redisTemplate.opsForHash() :
            this.hashOperations;
    }

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void saveOrUpdatePrice(String productId, ProductPrice price) {
        hashOperations.put(PRODUCT_PRICE, productId, price);
    }

    @Override
    public ProductPrice findPriceByProductId(String productId){
        return (ProductPrice) hashOperations.get(PRODUCT_PRICE, productId);
    }

}
