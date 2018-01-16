package com.myRetail.Dao;

import com.myRetail.Data.Price;

public interface PriceDao {
    void saveOrUpdatePrice(String productId, Price price);
    Price findPriceByProductId(String productId);
}
