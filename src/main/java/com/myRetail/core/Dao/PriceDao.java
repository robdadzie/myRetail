package com.myRetail.core.Dao;

import com.myRetail.core.Domain.ProductPrice;

public interface PriceDao {
    void saveOrUpdatePrice(String productId, ProductPrice price);
    ProductPrice findPriceByProductId(String productId);
}
