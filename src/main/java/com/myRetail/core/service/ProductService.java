package com.myRetail.core.service;

import com.myRetail.core.Domain.ProductPrice;
import com.myRetail.core.Domain.ProductDetails;

public interface ProductService {
    ProductPrice getProductPrice (String id);
    ProductDetails getProductDetails(String id);
    void saveOrUpdateProductPrice(String id, ProductPrice price);
}
