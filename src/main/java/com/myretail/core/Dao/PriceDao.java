package com.myretail.core.Dao;

import com.myretail.core.Domain.ProductPrice;

public interface PriceDao {
    void saveOrUpdatePrice(String productId, ProductPrice price);

    ProductPrice findPriceByProductId(String productId);
}
