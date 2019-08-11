package com.myretail.core.service;

import com.myretail.core.Domain.Product;
import com.myretail.core.Domain.ProductPrice;

public interface ProductService {
    ProductPrice getProductPrice (String id);

    Product getProductDetails(String id);

    void saveOrUpdateProductPrice(String id, ProductPrice price);
}
