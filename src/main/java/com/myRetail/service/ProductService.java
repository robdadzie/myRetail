package com.myRetail.service;

import com.myRetail.Data.Price;
import com.myRetail.Data.ProductDetails;

public interface ProductService {
    Price getProductPrice (String id);
    ProductDetails getProductDetails(String id);
    void saveOrUpdateProductPrice(String id, Price price);
}
